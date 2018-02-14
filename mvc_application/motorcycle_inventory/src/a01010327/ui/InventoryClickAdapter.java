/**
 * a01010327_assignment2
 * InventoryClickAdapter.java
 * Jun 22, 2017
 * 10:30:24 AM
 */
package a01010327.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Inventory;
import a01010327.data.util.Utilities;
import a01010327.database.dao.InventoryDao;

/**
 * @author amir deris, a01010327 adapter for click events on list items in
 *         JList. Provides edit functionality when a list item is clicked
 */

public class InventoryClickAdapter extends MouseAdapter {

	private static final Logger LOG = LogManager.getLogger();
	private Inventory clickedItem;
	private int index;
	private JList<String> theList;

	/**
	 * no-argument constructor
	 */
	public InventoryClickAdapter() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void mousePressed(MouseEvent e) {
		theList = (JList<String>) e.getSource();
		index = theList.locationToIndex(e.getPoint());
		if (index >= 0) {
			String s = (String) theList.getModel().getElementAt(index);
			String[] fields = s.split("\\|");
			String[] trimmedFields = Utilities.trimElements(fields);
			String motorcycleId = trimmedFields[0];
			String partNumber = trimmedFields[2];
			try {
				clickedItem = InventoryDao.getInstance().findById(motorcycleId, partNumber);
			} catch (Exception ex) {
				LOG.error(ex.getMessage());
			}
			createEditDialog();
		}
	}

	// creates an edit dialog for the clicked Inventory item.
	private void createEditDialog() {
		try {
			EditInventoryDialog dialog = new EditInventoryDialog(clickedItem);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

}
