package cardEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderContinueErrorException;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;


public class MainWindow {

	public JFrame frame;
	File jsonFile = new File(Main.workingDir + "/" + "Export.json");


	public MainWindow() {
		//Initialize UI
		frame = new JFrame("Manna Card Tool");





		//---------------------------------------------------------





		Object columnNames[] = 
			{ 
					"item", "description", "coverURL", "price", "category" 
			};
		JTable table = new JTable(Data.rowData, columnNames);
		System.out.println(table.getColumnName(0));

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
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(mnFile);


		//Open JSON file Button and Logic
		JMenuItem mntmOpen = new JMenuItem(new AbstractAction("Open") {
			private static final long serialVersionUID = 3524663815542609927L;

			public void actionPerformed(ActionEvent e) {




				System.out.println(new File(Main.workingDir + "/" + "Export.json"));


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
		mntmOpen.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		mnFile.add(mntmOpen);








		//-------------------------------------------------------------------



		//Open from Dropbox
		JMenuItem mntmOpenFromDropbox = new JMenuItem(new AbstractAction("Open from DropBox") 
		{
			private static final long serialVersionUID = 8845507260090824169L;

			public void actionPerformed(ActionEvent e) {

				// Create Dropbox client

				DbxRequestConfig config = DbxRequestConfig.newBuilder("MannaCardEditor-DB/0.1").build();
				DbxClientV2 client = new DbxClientV2(config , Main.ACCESS_TOKEN);



				// Get current account info
				FullAccount account = null;
				try {
					account = client.users().getCurrentAccount();
				} catch (Exception e1) {e1.printStackTrace();}
				System.out.println(account.getName().getDisplayName());




				// Get files and folder metadata from Dropbox root directory
				ListFolderResult result = null;
				try {
					result = client.files().listFolder("");
				} catch (Exception e1) {e1.printStackTrace();}


				while (true) {
					for (Metadata metadata : result.getEntries()) {
						System.out.println("Folder: " + metadata.getPathLower());
					}

					if (!result.getHasMore()) {
						break;
					}

					try {
						result = client.files().listFolderContinue(result.getCursor());
					} catch (Exception e1) {e1.printStackTrace();}
				}
				
				
				// Upload "test.txt" to Dropbox
				try (InputStream in = new FileInputStream(Main.workingDir + "/file1.json")) {
					FileMetadata metadata = client.files().uploadBuilder("/file1.json")
							.uploadAndFinish(in);
				} catch (Exception e1) {e1.printStackTrace();}
			}
		});
		mnFile.add(mntmOpenFromDropbox);






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
		mntmSave.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		mnFile.add(mntmSave);




		//---------------------------------------------




		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		mnFile.add(mntmExit);
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frame.getContentPane(), scrollPane, table}));
		frame.setVisible(true);

	}

}
