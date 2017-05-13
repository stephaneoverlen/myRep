package ON.Application;

import ON.Geography.Map;
import ON.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame{

	private Panel pan;
	private JButton ajouterVille = new JButton("Ajouter");
	private JButton supprimerVille = new JButton("Supprimer");
	private JButton ajouterLien = new JButton("Ajouter");
	private JButton supprimerLien = new JButton("Supprimer");
	private JPanel myBorderLayout = new JPanel();
	private JLabel titre = new JLabel("Notre beau pays");
	private JTextField villeTF = new JTextField("Ville");
	private JTextField latTF = new JTextField("Latitude");
	private JTextField lonTF = new JTextField("Longitude");
	private JTextField lien1TF = new JTextField("Ville1");
	private JTextField lien2TF = new JTextField("Ville2");
	private JTextField PoidsTF = new JTextField("Poids");
	private JLabel labelVille = new JLabel("Ville");
	private JLabel labelLien = new JLabel("Lien");
	private String Icon = "/logo/icons.png";


  
	public GUI(Map myMap){

		pan = new Panel(myMap);
		this.setTitle("Animation");
		this.setSize(800, 400);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage( new ImageIcon((getClass().getResource(Icon))).getImage());
		
		JPanel b1 = new JPanel();
		JPanel b2 = new JPanel();
		JPanel b3 = new JPanel();
		JPanel b4 = new JPanel();
		JPanel b5 = new JPanel();
		JPanel b6 = new JPanel();
		JPanel b7 = new JPanel();
		JPanel b8 = new JPanel();
		JPanel b9 = new JPanel();

		b1.setLayout(new BoxLayout(b1, BoxLayout.LINE_AXIS));
		b2.setLayout(new BoxLayout(b2, BoxLayout.LINE_AXIS));
		b3.setLayout(new BoxLayout(b3, BoxLayout.LINE_AXIS));
		b4.setLayout(new BoxLayout(b4, BoxLayout.LINE_AXIS));
		b5.setLayout(new BoxLayout(b5, BoxLayout.LINE_AXIS));
		b6.setLayout(new BoxLayout(b6, BoxLayout.LINE_AXIS));
		b7.setLayout(new BoxLayout(b7, BoxLayout.LINE_AXIS));
		b8.setLayout(new BoxLayout(b8, BoxLayout.LINE_AXIS));
		b9.setLayout(new BoxLayout(b9, BoxLayout.PAGE_AXIS));
		
		b1.add(labelVille);
		b2.add(villeTF);
		b3.add(latTF);
		b3.add(lonTF);
		b4.add(ajouterVille);
		b4.add(supprimerVille);
		b5.add(labelLien);
		b6.add(lien1TF);
		b6.add(lien2TF);
		b7.add(PoidsTF);
		b8.add(ajouterLien);
		b8.add(supprimerLien);
		b9.add(b1);
		b9.add(b2);
		b9.add(b3);
		b9.add(b4);
		b9.add(b5);
		b9.add(b6);
		b9.add(b7);
		b9.add(b8);

		myBorderLayout.setBackground(Color.white);
		myBorderLayout.setLayout(new BorderLayout());
		myBorderLayout.add(pan, BorderLayout.CENTER);
		myBorderLayout.add(b9, BorderLayout.WEST);
		
		ajouterVille.addActionListener(new AjouterVilleBoutonListener(myMap));
		supprimerVille.addActionListener(new SupprierVilleBoutonListener(myMap));
		ajouterLien.addActionListener(new AjouterLienBoutonListener(myMap));
		supprimerLien.addActionListener(new SupprimerLienBoutonListener(myMap));

		villeTF.setPreferredSize(new Dimension(150, 30));
		latTF.setPreferredSize(new Dimension(150, 30));
		lonTF.setPreferredSize(new Dimension(150, 30));
		lien1TF.setPreferredSize(new Dimension(150, 30));
		lien2TF.setPreferredSize(new Dimension(150, 30));
		PoidsTF.setPreferredSize(new Dimension(150, 30));

		villeTF.setHorizontalAlignment(JTextField.CENTER);
		latTF.setHorizontalAlignment(JTextField.CENTER);
		lonTF.setHorizontalAlignment(JTextField.CENTER);
		lien1TF.setHorizontalAlignment(JTextField.CENTER);
		lien2TF.setHorizontalAlignment(JTextField.CENTER);
		PoidsTF.setHorizontalAlignment(JTextField.CENTER);

		villeTF.setMaximumSize(villeTF.getPreferredSize());
		latTF.setMaximumSize(villeTF.getPreferredSize());
		lonTF.setMaximumSize(villeTF.getPreferredSize());
		lien1TF.setMaximumSize(lien1TF.getPreferredSize());
		lien2TF.setMaximumSize(lien2TF.getPreferredSize());
		PoidsTF.setMaximumSize(PoidsTF.getPreferredSize());

		Font police = new Font("Tahoma", Font.BOLD, 16);
		titre.setFont(police);
		titre.setHorizontalAlignment(JLabel.CENTER);
		myBorderLayout.add(titre, BorderLayout.NORTH);
		this.setContentPane(myBorderLayout);
		this.setVisible(true);

        villeTF.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                villeTF.setText("");
            }
        });

        latTF.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){ latTF.setText("");
            }
        });

        lonTF.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                lonTF.setText("");
            }
        });

        lien1TF.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                lien1TF.setText("");
            }
        });

		lien2TF.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				lien2TF.setText("");
			}
		});

		PoidsTF.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				PoidsTF.setText("");
			}
		});
	}

	class AjouterVilleBoutonListener implements ActionListener{
		private Map virtualMap;
		public void actionPerformed(ActionEvent arg0){
			titre.setText("Ajouter la ville " + villeTF.getText());
			Utils.addCity(Utils.parseLine(villeTF.getText()),
					Utils.parseLine(lonTF.getText()),
					Utils.parseLine(latTF.getText()),
					virtualMap
			);
		}

		public AjouterVilleBoutonListener (Map virtualMap){
			this.virtualMap = virtualMap;
		}
	}
	
	class SupprierVilleBoutonListener implements ActionListener{
		private Map virtualMap;
		public void actionPerformed(ActionEvent e){
			titre.setText("Supprimer une ville");
			Utils.delCity(Utils.parseLine(villeTF.getText()), virtualMap);
		}
		public SupprierVilleBoutonListener (Map virtualMap){
			this.virtualMap = virtualMap;
		}

	}      

	class AjouterLienBoutonListener implements ActionListener{
		private Map virtualMap;
		public void actionPerformed(ActionEvent arg0){
			titre.setText("Ajouter un lien");
			Utils.addConnection(Utils.parseLine(lien1TF.getText()),
					Utils.parseLine(lien2TF.getText()),
					Utils.parseLine(PoidsTF.getText()),
					virtualMap
			);
		}
		public AjouterLienBoutonListener (Map virtualMap){
			this.virtualMap = virtualMap;
		}
	}

	class SupprimerLienBoutonListener implements ActionListener{
		private Map virtualMap;
		public void actionPerformed(ActionEvent arg0){
			titre.setText("Supprimer un lien");
			Utils.delConnection(Utils.parseLine(lien1TF.getText()), Utils.parseLine(lien2TF.getText()), virtualMap);
		}
		public SupprimerLienBoutonListener(Map virtualMap){
			this.virtualMap = virtualMap;
		}
	}
}
