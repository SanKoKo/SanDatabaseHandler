package san.db.handler;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;


public class SanDBHandler {
	private Database database;
	private Context context;
	
	private static Boolean secure=false;
	public static Boolean getSecure() {
		return secure;
	}
	public void setSecure(Boolean secure) {
		SanDBHandler.secure = secure;
	}

	public SanDBHandler(Context context) {
		this.context = context;
	}
	
	
	public void createDatabase(Database database) {
		this.database = database;
		create();
	}
	
	private void create(){

		if(database !=null) {
		if(database.addDatabaseName()!=null) {
		Table[] tb = database.addTable();
		if(tb!=null) {
			Column[] columns = tb[0].getColumns();
			if(columns !=null)
			{
					new CreateDatabase(context, database.addDatabaseName(), tb);

			} else {
				try {
					throw new SanDBHandlerErrorException("No column was found");
				} catch (SanDBHandlerErrorException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				throw new SanDBHandlerErrorException("No table was found");
			} catch (SanDBHandlerErrorException e) {
				e.printStackTrace();
			}
		}
	  } 
	 else {
		try {
			throw new SanDBHandlerErrorException("No database name was found");
		} catch (SanDBHandlerErrorException e) {
			e.printStackTrace();
		}
	}
	
	}else {
		throw new SanDBHandlerErrorException("You have to call createDatabase(params) method first!");
	}
	}

	public Table[] getTables(){

		if(database!=null)
		{
			return database.addTable();
		}
		return null;
	}

	public Table getTable(int tableIndex){
		if(database!=null)
		{
			Table[] tb = database.addTable();
			return tb[tableIndex];
		}
		return null;
	}

	public Column[] getColumns(Table table){
		if(database!=null)
		{
			return table.getColumns();
		}
		return null;
	}

	public Column getColumn(Table table, int columnIndex) {
		if(database != null) {
			Column[] columns = table.getColumns();
			return columns[columnIndex];
		}

		return null;
	}

	public String getDatabaseName() {
		if(database==null)
		{
			return null;
		}

		return database.addDatabaseName();
	}

	private byte[] bytes = null;

	public void exportDatabase(Activity activity, String name) {
		FileInputStream in=null;
		File dir = Environment.getDataDirectory();
		String DB_PATH = "/data/"+activity.getPackageName()+"/databases/";
		File file = new File(dir + DB_PATH, name);


		try {
			in= new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			assert in != null;
			bytes = new byte[in.available()];
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.read(bytes);

		} catch (IOException e) {
			e.printStackTrace();

		}

		try {
			File file1 = new File(Environment.getExternalStorageDirectory(),name);
			FileOutputStream fileOutputStream = new FileOutputStream(file1);
			fileOutputStream.write(bytes);
			fileOutputStream.close();
		} catch  (IOException e){

			e.printStackTrace();

	}
	}

	//public void createDatabaseByClass(Class<?>[] mClass,String databaseName){
	public void createDatabaseByClass(String databaseName,Class... objectClass){

		try {
			createFromClass(objectClass,databaseName);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}


	}

	private <T extends SanDbResult<?>> void createFromClass(Class<?>[] mClass, final String databaseName) throws IllegalAccessException, InstantiationException {
		final Table[] tables = new Table[mClass.length];
		int k=0;
		for(Class<?> sClass : mClass) {
			tables[k] = mTable(sClass);
			k++;
		}
		createDatabase(new Database() {
			@Override
			public Table[] addTable() {
				return tables;
			}

			@Override
			public String addDatabaseName() {
				return databaseName;
			}
		});

		create();
	}
	 static int getIteration() {
		return Build.ID.length() >0 ? Build.ID.length() : 90;
	}
	 
	  static Table mTable(Class<?> sClass)
	 {
			Field[] fields = sClass.getDeclaredFields();
			String fileName = sClass.getName();
			String tbName = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			Column[] columns = new Column[fields.length];
			int i =0;
			for(Field field : fields)
			{
				columns[i] = new Column(field.getName(),field.getType());
				if(field.isAnnotationPresent(SetColumnAttr.class))
				{
					SetColumnAttr setColumnAttr = field.getAnnotation(SetColumnAttr.class);
					if(setColumnAttr.setPrimary()) {columns[i].setPrimary(true);}
					if(setColumnAttr.setNotNull()) { columns[i].setNotNull(true);}
					if(setColumnAttr.setUnique()){columns[i].setUnique(true);}
					if(setColumnAttr.setAutoIncrement()){columns[i].setAutoIncrease(true);}

				}
				i++;
			}
		 return new Table(tbName,columns);
	 }
	 
	 public void createDatabaseFromAsset(Activity activity,String databaseName,Class<?> defaultTableClass)
	 {
		 createFromAsset(activity,databaseName,defaultTableClass);
	 }
	private void createFromAsset(Activity activity, String databaseName,Class<?> defaultTableClass) {

			Table tb = mTable(defaultTableClass);
			new CreateDatabase(activity,databaseName,tb);

	}
}
