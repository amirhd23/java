/**
 * a01010327_assignment2
 * MainFrame.java
 * Jun 20, 2017
 * 5:20:43 PM
 */
package a01010327.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Inventory;
import a01010327.data.util.Utilities;
import a01010327.database.dao.InventoryDao;
import net.miginfocom.swing.MigLayout;
import javax.swing.KeyStroke;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

/**
 * @author amir deris, a01010327 main frame of the application
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = -7499897450340336288L;
	private static final Logger LOG = LogManager.getLogger();
	private JPanel contentPane;
	private JCheckBox chckboxDescending;
	private String makeFilter;

	/**
	 * creates the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setLocationRelativeTo(null);
		setTitle("BCMC Application");
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][354.00]", "[][]"));
		createMenuBar();

		JLabel lblBcmcApplicationUse = new JLabel("BCMC Application: please use the options to navigate.");
		lblBcmcApplicationUse.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBcmcApplicationUse.setForeground(Color.WHITE);
		contentPane.add(lblBcmcApplicationUse, "cell 2 1");
	}

	private void createMenuBar() {
		JMenuBar mainMenuBar = new JMenuBar();
		setJMenuBar(mainMenuBar);
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LOG.info("exitting the application.");
				System.exit(0);
			}
		});
		quitItem.setMnemonic(KeyEvent.VK_Q);
		fileMenu.add(quitItem);
		mainMenuBar.add(fileMenu);

		JMenu dataMenu = new JMenu("Data");
		dataMenu.setMnemonic(KeyEvent.VK_D);

		JMenuItem customerItem = new JMenuItem("Customers");
		customerItem.setMnemonic(KeyEvent.VK_C);
		customerItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "Customer report feature is not available yet.",
						"Feature Not Available", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JMenuItem serviceItem = new JMenuItem("Service");
		serviceItem.setMnemonic(KeyEvent.VK_S);
		serviceItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "Service report feature is not available yet.",
						"Feature Not Available", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JMenuItem inventoryItem = new JMenuItem("Inventory");
		inventoryItem.setMnemonic(KeyEvent.VK_I);
		inventoryItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Set<String> data = findRelevantInventoryData(chckboxDescending.isSelected(), false, false,
							makeFilter);
					InventoryDialog dialog = new InventoryDialog(data);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					LOG.error(ex.getMessage());
				}
			}
		});

		dataMenu.add(customerItem);
		dataMenu.add(serviceItem);
		dataMenu.add(inventoryItem);
		mainMenuBar.add(dataMenu);

		JMenu reportMenu = new JMenu("Report");
		reportMenu.setMnemonic(KeyEvent.VK_R);

		JMenuItem totalItem = new JMenuItem("Total");
		totalItem.setMnemonic(KeyEvent.VK_T);
		totalItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double value = InventoryDao.getInstance().getTotalInventoryValue();
				JOptionPane.showMessageDialog(MainFrame.this, String.format("Total value of inventory: %,.2f", value),
						"Total Value", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		reportMenu.add(totalItem);

		chckboxDescending = new JCheckBox("Descending");
		chckboxDescending.setHorizontalAlignment(SwingConstants.RIGHT);
		reportMenu.addSeparator();
		reportMenu.add(chckboxDescending);
		reportMenu.addSeparator();

		JMenuItem byDescriptionItem = new JMenuItem("By Description");
		byDescriptionItem.setMnemonic(KeyEvent.VK_P);

		byDescriptionItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Set<String> data = findRelevantInventoryData(chckboxDescending.isSelected(), true, false,
							makeFilter);
					InventoryDialog dialog = new InventoryDialog(data);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					LOG.error(ex.getMessage());
				}
			}
		});

		reportMenu.add(byDescriptionItem);

		JMenuItem byCountItem = new JMenuItem("By Count");
		byCountItem.setMnemonic(KeyEvent.VK_O);
		byCountItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Set<String> data = findRelevantInventoryData(chckboxDescending.isSelected(), false, true,
							makeFilter);
					InventoryDialog dialog = new InventoryDialog(data);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					LOG.error(ex.getMessage());
				}
			}
		});

		reportMenu.add(byCountItem);

		JMenuItem makeFilterItem = new JMenuItem("Make");
		makeFilterItem.setMnemonic(KeyEvent.VK_M);
		makeFilterItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(MainFrame.this, "Enter the make: ");
				if (name == null || name.equals("")) {
					makeFilter = null;
					return;
				}
				// validating the entered make
				Set<String> allValidMakes = new HashSet<String>();
				try {
					allValidMakes = InventoryDao.getInstance().findAllMotorcycleMakes();
				} catch (SQLException ex) {
					LOG.error(ex.getMessage());
				}
				boolean isMakeValid = false;
				for (String m : allValidMakes) {
					if (name != null && name.trim().equals(m)) {
						isMakeValid = true;
						break;
					}
				}
				if (!isMakeValid) {
					JOptionPane.showMessageDialog(MainFrame.this, "The entered make is not valid.", "Invalid Make",
							JOptionPane.ERROR_MESSAGE);
				}
				if (isMakeValid) {
					makeFilter = name.trim();
				}
			}
		});

		reportMenu.add(makeFilterItem);

		mainMenuBar.add(reportMenu);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this,
						"BCMC Application - Assignment 2\nWritten by Amir Deris\nID: A01010327", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(aboutItem);
		mainMenuBar.add(helpMenu);
	}

	/**
	 * finds relevant Inventory data (depending on selected options) to show
	 * 
	 * @return a Set<String>, containing String representation of all relevant
	 *         Inventory data.
	 */
	private Set<String> findRelevantInventoryData(boolean isDescending, boolean sortByDescription, boolean sortByCount,
			String filterByMake) {
		Set<Inventory> dataSet = InventoryDao.getInstance().getInventoryData(isDescending, sortByDescription,
				sortByCount, filterByMake);
		return Utilities.convert(dataSet);
	}

}
