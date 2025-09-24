package system.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class ScalePanel extends JPanel{

	private static final long serialVersionUID = -2816178521634984105L;
	private BigDecimal scale, iterator;
	private JLabel txtScale;
	private JButton btnUp, btnDown;

	public ScalePanel() {
		setLayout(new BorderLayout(5, 5));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		scale 		= new BigDecimal(2.0);
		iterator 	= new BigDecimal(0.1);
		txtScale 	= new JLabel("scale: " + scale.floatValue());
		btnDown 	= new JButton("-");
		btnUp 		= new JButton("+");
		
		txtScale.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.gray),
				BorderFactory.createEmptyBorder(0, 8, 0, 8)
		));
		txtScale.setBackground(Color.white);
		txtScale.setOpaque(true);

		btnDown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scale = scale.subtract(iterator);

				if(scale.compareTo(iterator) == -1) {
					scale = new BigDecimal(iterator.floatValue());
					Toolkit.getDefaultToolkit().beep();
				}
				
				float val = scale.floatValue();
				txtScale.setText("scale: " + val);
				onScaleChanged(val);
			}
			
		});
		
		btnUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scale = scale.add(iterator);
				
				float val = scale.floatValue();
				txtScale.setText("scale: " + val);
				onScaleChanged(val);
			}
			
		});
		
		add(txtScale, BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		btnPanel.add(btnDown);
		btnPanel.add(btnUp);
		
		add(btnPanel, BorderLayout.EAST);
		
	}
	
	public abstract void onScaleChanged(float scale);
	
}
