/**
 * a01010327_assignment2
 * InventoryDialog.java
 * Jun 20, 2017
 * 5:55:48 PM
 */
package a01010327.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 * @author amir deris, a01010327 shows a list of inventory to user.
 */
public class InventoryDialog extends JDialog {

	private static final long serialVersionUID = -7204257875907910670L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * creates the dialog.
	 * 
	 * @param inventoryData,
	 *            a Set<Inventory> used as data for displaying.
	 */
	public InventoryDialog(Set<String> inventoryData) {
		setBounds(100, 100, 1100, 600);
		setTitle("Inventory report");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow][]", "[][grow]"));
		{
			// add inventory data
			DefaultListModel<String> tempList = new DefaultListModel<>();
			for (String s : inventoryData) {
				tempList.addElement(s);
			}
			JList<String> inventoryList = new JList<String>(tempList);
			inventoryList.setFont(new Font("monospaced", Font.BOLD, 17));

			inventoryList.addMouseListener(new InventoryClickAdapter());

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(inventoryList);
			contentPanel.add(scrollPane, "cell 1 1,grow");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}

		}
	}

}
