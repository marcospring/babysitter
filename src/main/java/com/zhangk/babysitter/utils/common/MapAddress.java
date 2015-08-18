package com.zhangk.babysitter.utils.common;

public class MapAddress {
	private String status;
	private Result result;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public class Result {
		private Location location;
		private String formatted_address;
		private String business;
		private AddressComponent addressComponent;
		private int cityCode;

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public String getFormatted_address() {
			return formatted_address;
		}

		public void setFormatted_address(String formatted_address) {
			this.formatted_address = formatted_address;
		}

		public String getBusiness() {
			return business;
		}

		public void setBusiness(String business) {
			this.business = business;
		}

		public AddressComponent getAddressComponent() {
			return addressComponent;
		}

		public void setAddressComponent(AddressComponent addressComponent) {
			this.addressComponent = addressComponent;
		}

		public int getCityCode() {
			return cityCode;
		}

		public void setCityCode(int cityCode) {
			this.cityCode = cityCode;
		}

		public class Location {
			private String lng;
			private String lat;

			public String getLng() {
				return lng;
			}

			public void setLng(String lng) {
				this.lng = lng;
			}

			public String getLat() {
				return lat;
			}

			public void setLat(String lat) {
				this.lat = lat;
			}

		}

		public class AddressComponent {
			private String city;
			private String direction;
			private String distance;
			private String district;
			private String province;
			private String street;
			private String street_number;

			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public String getDirection() {
				return direction;
			}

			public void setDirection(String direction) {
				this.direction = direction;
			}

			public String getDistance() {
				return distance;
			}

			public void setDistance(String distance) {
				this.distance = distance;
			}

			public String getDistrict() {
				return district;
			}

			public void setDistrict(String district) {
				this.district = district;
			}

			public String getProvince() {
				return province;
			}

			public void setProvince(String province) {
				this.province = province;
			}

			public String getStreet() {
				return street;
			}

			public void setStreet(String street) {
				this.street = street;
			}

			public String getStreet_number() {
				return street_number;
			}

			public void setStreet_number(String street_number) {
				this.street_number = street_number;
			}

		}
	}

}
