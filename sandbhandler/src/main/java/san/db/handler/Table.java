package san.db.handler;


public class Table {
    private String tbName;
    private Column[] columns;
    
    public Table(String tableName,Column[] columns) {
        this.columns = columns;
        this.tbName = tableName;
        
       //System.out.println("tb:"+tableName + "\t"+"coloumn:"+Arrays.toString(columns)); 
    }

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public Column[] getColumns() {
		return columns;
	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
	}



}

/*interface Table {
	public TableName[] addTable();
}*/