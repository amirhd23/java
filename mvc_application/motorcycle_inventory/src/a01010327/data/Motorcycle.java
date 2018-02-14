/**
 * a01010327_assignment1
 * Motorcycle.java
 * May 19, 2017
 * 10:50:08 AM
 */
package a01010327.data;

/**
 * @author amir deris, a01010327 class representing a Motorcycle
 */
public class Motorcycle implements Comparable<Motorcycle> {
	private String id;
	private String make;
	private String model;
	private int year;
	private String serialNumber;
	private int mileage;
	private Customer owner;

	/**
	 * no-argument constructor
	 */
	public Motorcycle() {
		super();
	}

	/**
	 * constructor for all fields
	 * 
	 * @param make,
	 *            a String
	 * @param model,
	 *            a String
	 * @param id,
	 *            a String
	 * @param year,
	 *            an int
	 * @param serialNumber,
	 *            a String
	 * @param mileage,
	 *            an int
	 * @param owner,
	 *            a Customer
	 */
	public Motorcycle(String make, String model, String id, int year, String serialNumber, int mileage,
			Customer owner) {
		setMake(make);
		setModel(model);
		setId(id);
		setYear(year);
		setSerialNumber(serialNumber);
		setMileage(mileage);
		setOwner(owner);
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make
	 *            the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber
	 *            the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return the mileage
	 */
	public int getMileage() {
		return mileage;
	}

	/**
	 * @param mileage
	 *            the mileage to set
	 */
	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	/**
	 * @return the owner
	 */
	public Customer getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Motorcycle [id=" + id + ", make=" + make + ", model=" + model + ", year=" + year + ", serialNumber="
				+ serialNumber + ", mileage=" + mileage + ", owner=" + owner + "]";
	}

	/**
	 * comparison is based on mileage
	 */
	@Override
	public int compareTo(Motorcycle arg0) {
		if (this.equals(arg0)) {
			return 0;
		} else if (mileage - arg0.mileage > 0) {
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + mileage;
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((serialNumber == null) ? 0 : serialNumber.hashCode());
		result = prime * result + year;
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
		Motorcycle other = (Motorcycle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (mileage != other.mileage)
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (serialNumber == null) {
			if (other.serialNumber != null)
				return false;
		} else if (!serialNumber.equals(other.serialNumber))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

}
