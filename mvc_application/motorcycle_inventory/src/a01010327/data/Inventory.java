/**
 * a01010327_assignment1
 * Inventory.java
 * May 19, 2017
 * 10:56:16 AM
 */
package a01010327.data;

/**
 * @author amir deris, a01010327 class representing motorcycle parts (inventory)
 */
public class Inventory implements Comparable<Inventory> {
	private String motorcycleId;
	private String description;
	private String partNumber;
	private double price;
	private int quantity;

	/**
	 * no-argument constructor
	 */
	public Inventory() {
		super();
	}

	/**
	 * constructor for all fields
	 * 
	 * @param motorcycleId,
	 *            a String
	 * @param description,
	 *            a String
	 * @param partNumber,
	 *            a String
	 * @param price,
	 *            a double
	 * @param quantity,
	 *            an int
	 */
	public Inventory(String motorcycleId, String description, String partNumber, double price, int quantity) {
		super();
		setMotorcycleId(motorcycleId);
		setDescription(description);
		setPartNumber(partNumber);
		setPrice(price);
		setQuantity(quantity);
	}

	/**
	 * inner class that uses Builder pattern to construct an Inventory object
	 * 
	 * @author amir deris, a01010327
	 *
	 */
	public static class Builder {
		// required fields
		private String motorcycleId;
		private String partNumber;

		// optional fields
		private String description;
		private double price;
		private int quantity;

		/**
		 * Constructor of the inner class. motorcycleId and partNumber are the
		 * required fields.
		 * 
		 * @param motorcycleId,
		 *            a String
		 * @param partNumber,
		 *            a String
		 */
		public Builder(String motorcycleId, String partNumber) {
			this.motorcycleId = motorcycleId;
			this.partNumber = partNumber;
		}

		/**
		 * 
		 * @param val
		 * @return
		 */
		public Builder description(String val) {
			description = val;
			return this;
		}

		/**
		 * 
		 * @param val
		 * @return
		 */
		public Builder price(double val) {
			price = val;
			return this;
		}

		/**
		 * 
		 * @param val
		 * @return
		 */
		public Builder quantity(int val) {
			quantity = val;
			return this;
		}

		/**
		 * 
		 * @return the buit Inventory object
		 */
		public Inventory build() {
			return new Inventory(this);
		}

	}

	private Inventory(Builder builder) {
		motorcycleId = builder.motorcycleId;
		partNumber = builder.partNumber;
		description = builder.description;
		quantity = builder.quantity;
		price = builder.price;
	}

	/**
	 * @return the motorcycleId
	 */
	public String getMotorcycleId() {
		return motorcycleId;
	}

	/**
	 * @param motorcycleId
	 *            the motorcycleId to set
	 */
	public void setMotorcycleId(String motorcycleId) {
		this.motorcycleId = motorcycleId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * @param partNumber
	 *            the partNumber to set
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Inventory [motorcycleId=" + motorcycleId + ", description=" + description + ", partNumber=" + partNumber
				+ ", price=" + price + ", quantity=" + quantity + "]";
	}

	/**
	 * comparison is based on motorcycleId
	 */
	@Override
	public int compareTo(Inventory o) {
		if (this.equals(o)) {
			return 0;
		} else if (motorcycleId.compareTo(o.motorcycleId) > 0) {
			return 1;
		} else {
			return -1;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((motorcycleId == null) ? 0 : motorcycleId.hashCode());
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (motorcycleId == null) {
			if (other.motorcycleId != null)
				return false;
		} else if (!motorcycleId.equals(other.motorcycleId))
			return false;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

}
