package san.db.handler;



public class Column {
    private String columnName;
    private String columnType;
    private Class<?> type;
    private Boolean primary=false,autoIncrease=false,unique=false,notnull=false;
    public Column(String columnName, Class<?> type) {
        this.columnName = columnName;
        this.type = type;
    }

    public Column(String columnName, String columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }
    
    
    
    public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Column setPrimary() {
		
		Column column;
		if(getColumnType()==null){
			column = new Column(getColumnName(), getType());
		} else {
			column = new Column(getColumnName(), getColumnType());
		}
		column.setPrimary(true);
		column.setUnique(this.getUnique());
		column.setNotNull(this.getNotNull());
		column.setAutoIncrease(this.getAutoIncrease());
		return column;
	}

	public Column setAutoIncrement() {
		
		Column column;
		if(getColumnType()==null){
			column = new Column(getColumnName(), getType());
		} else {
			column = new Column(getColumnName(), getColumnType());
		}
		column.setPrimary(this.getPrimary());
		column.setNotNull(this.getNotNull());
		column.setAutoIncrease(true);
		return column;
	}

	public Column setNotNull() {

		Column column;
		if(getColumnType()==null){
			column = new Column(getColumnName(), getType());
		} else {
			column = new Column(getColumnName(), getColumnType());
		}
		column.setNotNull(true);
		column.setUnique(this.getUnique());
		column.setPrimary(this.getPrimary());
		column.setAutoIncrease(this.getAutoIncrease());
		return column;
	}

	public Column setUnique() {

		Column column ;
		if(getColumnType()==null){
			column = new Column(getColumnName(), getType());
		} else {
			column = new Column(getColumnName(), getColumnType());
		}
		column.setUnique(true);
		column.setNotNull(this.getNotNull());
		column.setPrimary(this.getPrimary());
		column.setAutoIncrease(this.getAutoIncrease());
		return column;
	}

	public Boolean getPrimary() {
		return primary;
	}

	public Boolean getAutoIncrease() {
		return autoIncrease;
	}

	protected void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	protected void setAutoIncrease(Boolean autoIncrease) {
		this.autoIncrease = autoIncrease;
	}

	public Boolean getUnique() {
		return unique;
	}

	protected void setUnique(Boolean unique) {
		this.unique = unique;
	}

	public Boolean getNotNull() {
		return notnull;
	}

	protected void setNotNull(Boolean notnull) {
		this.notnull = notnull;
	}

	public String toString(){
		return getColumnName() +" ::: "+getType();
	}
	

	
	
}
