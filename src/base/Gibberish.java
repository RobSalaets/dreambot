package base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Gibberish{

	private String[] greetings = new String[] { "hiese", "hey", "ej", "ey", "jo", "yo", "hoi", "hallo", "yo", "joe", "ela" };
	private String[] greetings_plus = new String[] { "boi", "m8", "dogg", "vriend", "mate", "boike" };
	private String[] m_nouns = new String[] { "flappen", "%d00k", "%d mil", "items", "geld", "iets", "logs", "runes", "armor", "niks" };
	private String[] a_nouns = initANouns();
	private String[] places = new String[] { "Varrock", "Edgeville", "Falador", "wereld 31%d", "wildy" };
	private String[] prepos = new String[] { "in", "naar", "boven", "door", "onder", "achter" };
	private String[] adjs = new String[] { "aight", "dom", "stupid", "goed", "slecht" };
	private String[] short_concl = new String[] { "ok", "tis goe", "chill", "aight", "allright dan", "zal wel", "ok dan", "pff nee", "meh" };
	private String[] start_ans = new String[] { "ja", "nee", "ok" };
	private String[] links = new String[] { "en", "maar", "dus", "daarom dat ", "drmee dat " };
	private String[] pers_pronouns = new String[] { "ik:0.1:1", "ge:0.1:2", "gy:0.2:2", "gij:0.25:2", "gelle:0.4:2", "die:0.4:3", "dieje:0.3:3", "mn make:0.2:3"};

	private HashMap<String, String> verb_map = initVerbMap();
	private HashMap<String, String[]> full_verb_map = initFullVerbMap();

	private Random rand;

	private String[] initANouns(){
		List<String> list = Arrays.asList(new String[] { "quest", "banken", "trade", "niks" });
		return list.toArray(new String[list.size()]);
	}

	private HashMap<String, String> initVerbMap(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("kom", "p,mh:0.2:t,de");
		map.put("wil", "ph,m:0.2:t,de");
		map.put("is", "p,e:0.2");
		map.put("ga", "p,ph,mh:0.1:at,de");
		map.put("moet", "ph,mh,a:0.1:,e");
		map.put("kan", "ph,mh,a:0.1:--unt,--unde");
		map.put("doe", "a:0.4:t,de");
		map.put("vind", "m:0.2:t,e");
		map.put("fix", "mh,a:0.2:t,te");
		map.put("geef", "m:0.1:t,de");
		map.put("koop", "m:0.3:t,te");
		map.put("maak", "m:0.1:t,te");
		map.put("pk", "p:0.6:t,te");
		return map;
	}

	private HashMap<String, String[]> initFullVerbMap(){
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		map.put("p", new String[] { "komen", "gaan", "zijn", "zien" });
		map.put("a", new String[] { "doen", "kunnen", "fixen", "zien", "trainen" });
		map.put("m", new String[] { "fixen", "banken", "geven", "traden", "kopen", "grinden", "maken" });
		return map;
	}

	public Gibberish(){
		this.rand = new Random();
	}

	private String getGreeting(){
		return getRandom(greetings) + getWithChance(0.3, greetings_plus, " ", "");
	}

	private String getAnswer(){
		if(chance(0.4))
			return getRandom(short_concl);
		String link = getRandom(links);
		String startAns = getWithChance(0.6, start_ans, "", (chance(0.5) ? ", " : " ") + link.trim() + " ");
		return startAns + getSentence((link.endsWith(" ")  && startAns.length() > 0)? "sov" : "svo");
	}

	private String getQuestion(){

		String start = "";
		String[] verb_object = getVerbWithObject();
		if(chance(0.4))
			start = getDTEFor(verb_object[0]);
		else{
			String pronoun;
			String person;
			String[] pronoun_label;
			do{
				pronoun_label = getRandom(pers_pronouns).split(":");
				pronoun = pronoun_label[0];
				person = pronoun_label[2];
			}while(chance(Double.parseDouble(pronoun_label[1])));
			String verb = verb_object[0];
			String[] pv_object = getTFor(pronoun, person, verb);
			start = pv_object[1] + pv_object[0];
		}
		return start + String.format(verb_object[1], rand.nextInt(10) + 1) + "?";
	}

	private String getSentence(String order){
		String pronoun;
		String person;
		String[] pronoun_label;
		do{
			pronoun_label = getRandom(pers_pronouns).split(":");
			pronoun = pronoun_label[0];
			person = pronoun_label[2];
		}while(chance(Double.parseDouble(pronoun_label[1])));

		String[] verb_object = getVerbWithObject();
		String[] pv_object = getTFor(pronoun, person, verb_object[0]);
		String sentence = "";
		for(char c : order.toCharArray()){
			if(c == 's')
				sentence += pv_object[0];
			else if(c == 'o')
				sentence += String.format(verb_object[1], rand.nextInt(10) + 1) + " ";
			else if(c == 'v')
				sentence += pv_object[1];
		}
		if(chance(0.3)){
			String link = getRandom(links);
			sentence += link.trim() + " " + getSentence(link.endsWith(" ") ? "sov" : "svo");
		}
		return sentence;
	}

	private String respell(String input){
		return input.substring(0, 1).toUpperCase() + input.substring(1).trim();
	}

	public String continueConv(String input){
		if(chance(0.15))
			return null;
		if(input == null){
			return respell(getGreeting());
		}
		if(input.endsWith("?")){
			return respell(getAnswer());
		}
		if(chance(0.60)){
			return respell(getQuestion());
		}else return respell(getSentence("svo"));
	}

	private String getWithChance(double chance, String[] array, String prefix, String suffix){
		return chance(chance) ? prefix + getRandom(array) + suffix : "";
	}

	private String getRandom(String[] array){
		return array[rand.nextInt(array.length)];
	}

	private boolean chance(double chance){
		return rand.nextFloat() < chance;
	}

	private String[] getTFor(String pronoun, String person, String verb){

		if(verb.equals("is")){
			switch(person){
				case "1":
					return new String[]{"ik ", "ben "};
				case "2":
					return new String[]{pronoun + " ", "bent "};
				case "3":
					return new String[]{pronoun + " ", "is "};
			}
		}else{
			String ml = verb_map.get(verb).split(":")[2].split(",")[0];
			String orig_verb = verb;
			int index = ml.lastIndexOf("-") + 1;
			if(ml.startsWith("-")){
				verb = verb.substring(0, verb.length() - index);
			}
			switch(person){
				case "1":
					return new String[]{pronoun + " ", orig_verb + " "};
				default:
					return new String[]{pronoun + " ", verb + verb_map.get(orig_verb).split(":")[2].split(",")[0].substring(index) + " "};
			}
		}
		return new String[]{pronoun + " ", verb + "t "};
	}

	private String[] getVerbWithObject(){
		String verb;
		String[] label;
		do{
			verb = getRandom(verb_map.keySet().toArray(new String[verb_map.size()]));
			label = verb_map.get(verb).split(":");
		}while(chance(Double.parseDouble(label[1])));

		String[] tags = label[0].split(",");
		String tag = tags[rand.nextInt(tags.length)];
		if(tag.startsWith("p")){
			String object = getRandom(places);
			if(tag.endsWith("h"))
				object = (chance(0.5) ? getRandom(prepos) + " " : "") + object + " " + getRandom(full_verb_map.get("p"));
			else object = getRandom(prepos) + " " + object;
			return new String[] { verb, object };
		}else if(tag.startsWith("m")){
			String m_noun = getRandom(m_nouns);
			if(tag.endsWith("h"))
				m_noun += " " + getRandom(full_verb_map.get("m"));
			return new String[] { verb, m_noun };
		}else if(tag.startsWith("a")){
			return new String[] { verb, getRandom(a_nouns) + (chance(0.2) ? " " + getRandom(full_verb_map.get("a")) : "") };
		}else return new String[] { verb, getRandom(adjs) };

	}

	private String getDTEFor(String verb){
		if(verb.equals("is"))
			return "bende ";
		String ml = verb_map.get(verb).split(":")[2].split(",")[1];
		String orig_verb = verb;
		int index = ml.lastIndexOf("-") + 1;
		if(ml.startsWith("-")){
			verb = verb.substring(0, verb.length() - index);
		}
		return verb + verb_map.get(orig_verb).split(":")[2].split(",")[1].substring(index) + " ";
	}

	public static void main(String[] args){
		Gibberish g = new Gibberish();
		String input = null;
		for(int i = 0; i < 30; i++){
			input = g.continueConv(input);

			System.out.println(input == null ? "" : input);
		}
	}
}
