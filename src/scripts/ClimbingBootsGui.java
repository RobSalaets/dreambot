package scripts;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ClimbingBootsGui extends JFrame {

	private String accountName;

	public ClimbingBootsGui() {
		super("Script Settings");
		JPanel panel = new JPanel();
		setContentPane(panel);
		setSize(300, 100);
		setLocationRelativeTo(null);
		setVisible(true);
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel("Enter Trade recipient or leave blank:"));
		JTextField txtField = new JTextField(20);
		txtField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = txtField.getText();
				if(input.length() > 0)
					accountName = input;
				dispose();
			}
		});
		panel.add(txtField);
	}

	public String getTradeAccount() {
		return accountName;
	}
}
