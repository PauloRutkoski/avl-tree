package enums;

public enum Operation {
	FIND_CPF(0, "c"), FIND_NAME(1, "n"), FIND_DATE(2, "d"), CLOSE(3, "s") ;

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
