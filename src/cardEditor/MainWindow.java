package cardEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class MainWindow {

	public JFrame frame;

	public MainWindow() {
		//Initialize UI
		frame = new JFrame("Manna Card Tool");





		//---------------------------------------------------------





		Object columnNames[] = 
			{ 
					"item", "description", "coverURL", "price", "category" 
			};
		JTable table = new JTable(Data.rowData, columnNames);


		table.getColumnModel().getColumn(0).setPreferredWidth(189);
		table.getColumnModel().getColumn(1).setPreferredWidth(233);
		table.getColumnModel().getColumn(2).setPreferredWidth(152);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(139);
		table.setFont(new Font("Helvetica", Font.PLAIN, 18));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(618, 504);
		frame.pack();


		//---------------------------------------------------------




		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnFile);


		//Open JSON file Button and Logic
		JMenuItem mntmOpen = new JMenuItem(new AbstractAction("Open") {
			private static final long serialVersionUID = 3524663815542609927L;

			public void actionPerformed(ActionEvent e) {




				System.out.println(new File(Main.workingDir + "/" + "Export.json"));

				File jsonFile = new File(Main.workingDir + "/" + "Export.json");
				List<CardData> card;

				//Create POJO
				card = Data.convert(jsonFile);
				
				
				//Clean POJO
				card = Data.cleanCard(card);
				System.out.println("Card: " + card.toString());
				
				for (int i = 0; i < table.getRowCount(); i++) {
						table.setValueAt(card.get(i), i, 0);
						System.out.println(card.get(i));
					
				}


			}
		});
		mntmOpen.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnFile.add(mntmOpen);






		//-----------------------------------------------------






		//Save JSON File
		JMenuItem mntmSave = new JMenuItem(new AbstractAction("Save") {

			private static final long serialVersionUID = 3524663815542609927L;

			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {


				JSONArray itemList = new JSONArray();

				for (int j = 0; j < table.getRowCount(); j++) {
					JSONObject itemKeys = new JSONObject();

					for (int i = 0; i < table.getColumnCount(); i++) {
						itemKeys.put(table.getColumnName(i), table.getValueAt(j, i));

					}
					itemList.add(itemKeys);
				}

				System.out.println(itemList);
				Main.saveToJSON(itemList, "Export.json");
			}
		});
		mntmSave.setFont(new Font("Segoe UI", Font.PLAIN, 18));

		mnFile.add(mntmSave);




		//---------------------------------------------




		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnFile.add(mntmExit);
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frame.getContentPane(), scrollPane, table}));
		frame.setVisible(true);

	}

}
