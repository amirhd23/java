/**
 * a01010327_assignment1
 * Customer.java
 * May 19, 2017
 * 10:49:10 AM
 */
package a01010327.data;

import java.time.LocalDate;

/**
 * @author amir deris, a01010327
 *
 */
public class Customer {

	private int id;
	private String firstName;
	private String lastName;
	private String streetName;
	private String city;
	private String postalCode;
	private String phone;
	private String emailAddress;
	private LocalDate dateJoined;

	/**
	 * no-argument constructor
	 */
	public Customer() {
		super();
	}

	/**
	 * @param id,
	 *            an int
	 * @param firstName,
	 *            a String
	 * @param lastName,
	 *            a String
	 * @param streetName,
	 *            a String
	 * @param city,
	 *            a String
	 * @param postalCode,
	 *            a String
	 * @param phone,
	 *            a String
	 * @param emailAddress,
	 *            a String
	 * @param dateJoined,
	 *            a LocalDate
	 */
	public Customer(int id, String firstName, String lastName, String streetName, String city, String postalCode,
			String phone, String emailAddress, LocalDate dateJoined) {
		super();
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setStreetName(streetName);
		setCity(city);
		setPostalCode(postalCode);
		setPhone(phone);
		setEmailAddress(emailAddress);
		setDateJoined(dateJoined);
	}

	/**
	 * inner class that uses Builder pattern to construct a Customer
	 * 
	 * @author amir deris, a01010327
	 *
	 */
	public static class Builder {
		// required parameters
		private int id;
		private String phone;

		// Optional parameters - initialized to default values
		private String firstName;
		private String lastName;
		private String streetName;
		private String city;
		private String postalCode;
		private String emailAddress;
		private LocalDate dateJoined;

		/**
		 * Constructor of the inner class. id and phone are the required fields
		 * to construct the object
		 * 
		 * @param id,
		 *            an int
		 * @param phone,
		 *            a String
		 */
		public Builder(int id, String phone) {
			this.id = id;
			this.phone = phone;
		}

		/**
		 * 
		 * @param val
		 * @return
		 */
		public Builder firstName(String val) {
			firstName = val;
			return this;
		}

		/**
		 * 
		 * @param val
		 * @return
		 */
		public Builder lastName(String val) {
			lastName = val;
			return this;
		}

		/**
		 * 
		 * @param val
		 * @return
		 */
		public Builder streetName(String val) {
			streetName = val;
			return this;
		}

		/**
		 * 
		 * @param val
		 * @return
		 */
		public Builder city(String val) {
			city = val;
			return this;
		}

		/**
		 * 
		 * @param val
		 * @return
		 */
		public Builder postalCode(String val) {
			postalCode = val;
			return this;
		}

		/**
		 * 
		 * @param val
		 * @return
		 */
		public Builder emailAddress(String val) {
			emailAddress = val;
			return this;
		}

		/**
		 * 
		 * @param date
		 * @return
		 */
		public Builder dateJoined(LocalDate date) {
			dateJoined = date;
			return this;
		}

		/**
		 * 
		 * @return
		 */
		public Customer build() {
			return new Customer(this);
		}

	}

	private Customer(Builder builder) {
		id = builder.id;
		firstName = builder.firstName;
		lastName = builder.lastName;
		streetName = builder.streetName;
		city = builder.city;
		postalCode = builder.postalCode;
		phone = builder.phone;
		emailAddress = builder.emailAddress;
		dateJoined = builder.dateJoined;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * @param streetName
	 *            the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the dateJoined
	 */
	public LocalDate getDateJoined() {
		return dateJoined;
	}

	/**
	 * @param dateJoined
	 *            the dateJoined to set
	 */
	public void setDateJoined(LocalDate dateJoined) {
		this.dateJoined = dateJoined;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", streetName="
				+ streetName + ", city=" + city + ", postalCode=" + postalCode + ", phone=" + phone + ", emailAddress="
				+ emailAddress + ", dateJoined=" + dateJoined + "]";
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
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((dateJoined == null) ? 0 : dateJoined.hashCode());
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
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
		Customer other = (Customer) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (dateJoined == null) {
			if (other.dateJoined != null)
				return false;
		} else if (!dateJoined.equals(other.dateJoined))
			return false;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (streetName == null) {
			if (other.streetName != null)
				return false;
		} else if (!streetName.equals(other.streetName))
			return false;
		return true;
	}

}
