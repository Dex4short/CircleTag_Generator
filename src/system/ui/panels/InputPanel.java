package system.ui.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class InputPanel extends JPanel{
	private static final long serialVersionUID = 3687442493712813638L;
	private final JTextField txt_field;
	private final JButton btn;
	
	public InputPanel(int initialId) {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		txt_field = new JTextField(10);
		btn = new JButton("Generate");
		
		txt_field.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.gray),
				BorderFactory.createEmptyBorder(4, 5, 4, 5)
		));
		txt_field.setText(String.valueOf(initialId));
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					generateCircleTag(Integer.parseInt(txt_field.getText()));
				} catch (Exception ex) {
					ex.printStackTrace();
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
		
		add(txt_field);
		add(btn);
		
	}
	
	public abstract void generateCircleTag(int id);

}
