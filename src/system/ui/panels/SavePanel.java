package system.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import marker.CircleTag;
import marker.CircleTagIO;
import system.CircleTagGenerator;

public abstract class SavePanel extends JPanel{
	private static final long serialVersionUID = -8041548321936466519L;
	
	public SavePanel() {
		setLayout(new BorderLayout(5,5));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JTextField url_field = new JTextField();
		JButton save_btn = new JButton("Save");		
		
		url_field.setText("C:\\Users\\user\\Downloads\\Generated CircleTags\\");
		url_field.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.gray),
				BorderFactory.createEmptyBorder(4, 5, 4, 5)
		));
		
		save_btn.addActionListener(e -> onSave(url_field.getText()));
		
		add(url_field, BorderLayout.CENTER);
		add(save_btn, BorderLayout.EAST);
		
	}
	
	public abstract void onSave(String url);
	
}
