package enums;

public enum Operation {
	INSERT(0, "i"), SEARCH(1, "b"), REMOVE(2, "r"), REFERRALS(3, "e"), CLOSE(4, "s") ,CLEAN(5, "l");

	public static Operation fromString(String value) {
		if (value != null && !value.trim().isEmpty()) {
			for (Operation op : Operation.values()) {
				if (op.getValue().equals(value.toLowerCase())) {
					return op;
				}
			}
		}
		return null;
	}

	private Operation(int code, String value) {
		this.value = value;
		this.code = code;
	}

	private int code;
	private String value;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
