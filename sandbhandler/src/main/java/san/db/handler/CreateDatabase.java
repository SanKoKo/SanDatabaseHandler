package san.db.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

class CreateDatabase extends SQLiteOpenHelper{

	private static Table[] tables;
	private static Column[] columns;
	private static String dbName;
	@SuppressLint("StaticFieldLeak")
	private static Context context;
	private HashMap<String,Table> tableHash;
	private String[] columnDef = new String[4];
	@SuppressWarnings("static-access")
	CreateDatabase(Context context, String dbName, Table[] tables) {
		super(context, dbName, null, 1);
		this.context = context;
		this.tables=tables;
		tableHash = generateMaping();
		this.dbName = dbName;
	}

	@SuppressWarnings("static-access")
	CreateDatabase(Activity activity,String databaseName,Table tb) {
		super(activity.getApplicationContext(), databaseName, null, 1);
		this.context = activity.getApplicationContext();
		try {
			createFromAsset(activity,databaseName,tb);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HashMap<String, Table> generateMaping() {
		tableHash = new HashMap<>();
		for(Table table : tables)
		{
			tableHash.put(table.getTbName(),table);
		}
		return tableHash;
	}

	static CreateDatabase getInstance() {


		return new CreateDatabase(context,dbName,tables);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {

		for(Table table : tables) {
			columns = table.getColumns();

			for(Column column : columns)
			{
				if(column.getColumnType() == null){
					allInstanceCombine(column);
				}
			}
			String sql = "CREATE TABLE " + table.getTbName() + "( " + allCombine() + ");";
			if(SanDBHandler.getShowLog()){
				System.out.println("SanSQL Log : "+sql);
			}
			try {
				arg0.execSQL(sql);
			} catch (Exception e) {
				e.printStackTrace();
				//throw new SanDBHandlerErrorException(e.getMessage());

			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

	private void allInstanceCombine(Column column) {

            Class<?> columnType = column.getType();
            if (columnType.equals(Short.class) || columnType.equals(short.class)) {
               column.setColumnType("SHORT");
            }
            else if (columnType.equals(Integer.class) || columnType.equals(int.class)) {
            	column.setColumnType("INTEGER");
            }
            else if (columnType.equals(Long.class) || columnType.equals(long.class)) {
            	column.setColumnType("LONG");
            }
            else if (columnType.equals(Float.class) || columnType.equals(float.class)) {
            	column.setColumnType("FLOAT");
            }
            else if (columnType.equals(Double.class) || columnType.equals(double.class)) {
            	column.setColumnType("DOUBLE");
            }
            else if (columnType.equals(Boolean.class) || columnType.equals(boolean.class)) {
            	column.setColumnType("BOOLEAN");
            }
            else if (columnType.equals(char.class)) {
            	column.setColumnType("CHAR");
            }
            else if (columnType.equals(String.class)) {
            	column.setColumnType("NVARCHAR");
            }
            else if (columnType.equals(Date.class)) {
            	column.setColumnType("DATE()");
            }
            else if (columnType.equals(Blob.class)) {
            	column.setColumnType("BLOB");
            }
            else if (columnType.equals(Bitmap.class)) {
				column.setColumnType("BLOB");
			}
          else{
        	  column.setColumnType(String.valueOf(columnType).toUpperCase());
            }


	}
    private String allCombine() {
    	String str="";
		int last = columns.length;
        for(Column sColumn : columns)
        {
        	--last;

			String extraColumnDef="";
        	if(sColumn.getPrimary())
			{
				columnDef[0] = " PRIMARY KEY ";
			} else {columnDef[0] = null;}

			if(sColumn.getNotNull())
			{
				columnDef[1] = " NOT NULL ";
			} else {columnDef[1] = null;}

			if(sColumn.getUnique())
			{
				columnDef[2] = " UNIQUE ";
			} else {columnDef[2] = null;}

			if(sColumn.getAutoIncrease())
			{
				if(sColumn.getUnique())
				{
					columnDef[3] = null;
				} else {
					columnDef[3] = " AUTOINCREMENT ";
				}
			} else {columnDef[3] = null;}

				for(String strDef : columnDef)
				{
					if(strDef!=null)
					{
						extraColumnDef +=strDef;
					}
				}

			str += nReplace(sColumn.getColumnName()) +" "+nReplace(sColumn.getColumnType())+"  "+extraColumnDef;
        	if(last>0) {
        		str +=", ";
        	}
        }
        return str;
    }


	private String nReplace(String str) {str = str.replaceAll(" ","_");return str;}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	<T extends SanDbResult<T>> List<T> getAllData(Class<?> mClass, String tb, String selection, String[] selectionArgs) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		List<T> list= new ArrayList<>();

		if(dbName!=null) {

			if(tb==null)
			{
				tb = tables[0].getTbName();
			}
			String sql ="";
			int count=0;
			if(selection !=null && selectionArgs!=null){
				StringTokenizer stk = new StringTokenizer(selection,"?");
				StringBuilder sb = new StringBuilder();
				while(stk.hasMoreElements())
				{
					sb.append(stk.nextToken()).append("'").append(selectionArgs[count]).append("'");
					++count;
				}

				sql = "SELECT * FROM "+tb+" WHERE "+sb.toString();

			} else {
				sql = "SELECT * FROM "+tb;
			}

			if(SanDBHandler.getShowLog()){
				System.out.println("SanSQL Log : "+sql);
			}

			SQLiteDatabase sqLiteDatabase = getReadableDatabase();
			Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

			try {
				columns = tableHash.get(tb).getColumns();
			} catch (NullPointerException npe)
			{
				Table table = SanDBHandler.mTable(mClass);
				columns = table.getColumns();
			}


			//columns = new Column[cursor.getColumnCount()];
			for(int i=0;i<cursor.getColumnCount();i++)
			{
				columns[i].setColumnName(cursor.getColumnName(i));
			}

		HashMap<String, Class> mapper = new HashMap<>();
			List<String> listKey = new ArrayList<>();
		Class cls = Class.forName(mClass.getName());

		Method[] methods = cls.getDeclaredMethods();
		for(Method method : methods) {
				Class<?> type;
				String methodName;
				if(method.getName().startsWith("get")){
					type = method.getReturnType();
					methodName = "set"+method.getName().substring(3);
					listKey.add(methodName);
					mapper.put(methodName, type);
				}

			}

			listKey = arrangeList(listKey);
			cursor.moveToFirst();

			do {
				T entity	 = (T) cls.newInstance();
				for(int i=0;i<listKey.size();i++)
				{

					String key = listKey.get(i);
					Method method = cls.getDeclaredMethod(key, mapper.get(key));
			try {
				if (mapper.get(key).equals(int.class) || mapper.get(key).equals(Integer.class)) {
					method.invoke(entity, cursor.getInt(i));
				} else if (mapper.get(key).equals(String.class)) {
					if(SanDBHandler.getSecure()){
						method.invoke(entity, Encrypt.decrypt(Build.ID+"San99",cursor.getString(i)));	
					} else {
					method.invoke(entity, cursor.getString(i)); }
				} else if(mapper.get(key).equals(char.class))
				{
					method.invoke(entity, cursor.getString(i));
				}
				
				else if (mapper.get(key).equals(Blob.class)) {
					method.invoke(entity, (Object) cursor.getBlob(i));
				} else if (mapper.get(key).equals(Boolean.class) || mapper.get(key).equals(boolean.class)) {
					method.invoke(entity, cursor.getInt(i) > 0);
				} else if (mapper.get(key).equals(Bitmap.class)) {
					method.invoke(entity, ByteToBitmap.getImage(cursor.getBlob(i)));
				} else if (mapper.get(key).equals(Long.class) || mapper.get(key).equals(long.class)) {
					method.invoke(entity, cursor.getLong(i));
				} else if (mapper.get(key).equals(Double.class) || mapper.get(key).equals(double.class)) {
					method.invoke(entity, cursor.getDouble(i));
				} else if (mapper.get(key).equals(Float.class) || mapper.get(key).equals(float.class)) {
					method.invoke(entity, cursor.getFloat(i));
				} else {
					method.invoke(entity, cursor.getString(i));
				}
			}catch (CursorIndexOutOfBoundsException ciob){ciob.printStackTrace(); return null;}
				}
				list.add(entity);
			}while (cursor.moveToNext());
			cursor.close();
			sqLiteDatabase.close();
		} else {
			throw new SanDBHandlerErrorException("Database was not created!");
		}

		return list;
	}




	private List<String> arrangeList(List<String> listKey) {
		for(int c=0;c<columns.length;c++)
		{

			for(int d=c;d<listKey.size();d++)
			{
				if(listKey.get(d).contains(Upper(columns[c].getColumnName()))) {
					String temp = listKey.get(c);
					listKey.set(c,listKey.get(d));
					listKey.set(d,temp);
					break;
				}
			}


		}


		return listKey;
	}


	@SuppressWarnings("rawtypes")
	void add(SanDbResult sanDbResult, String tableName,Boolean update,String where,String[] whereArgs) throws NoSuchFieldException, IllegalAccessException {
		SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Field[] fields = sanDbResult.getClass().getDeclaredFields();
		HashMap<String,Column> mapColumn = new HashMap<>();

		Table  table = SanDBHandler.mTable(sanDbResult.getClass());
		columns = table.getColumns();
		//tableName = table.getTbName();
		/*try {
			columns = tableHash.get(tableName).getColumns();
		} catch (NullPointerException npe){
			 table = SanDBHandler.mTable(sanDbResult.getClass());
			columns = table.getColumns();
		}*/
		if(tableName==null)
		{
			tableName = table.getTbName();
		}

		for(Column column: columns)
		{
			mapColumn.put(column.getColumnName(),column);
		}

		ContentValues contentValues = new ContentValues();
        for(Field field : fields)
        {
			field = sanDbResult.getClass().getDeclaredField(field.getName());
			field.setAccessible(true);

			if(field.getType().equals(Bitmap.class)){
				contentValues.put(field.getName(), ByteToBitmap.getBytes((Bitmap) field.get(sanDbResult)));
			} else {
				try{
					if (!mapColumn.get(field.getName()).getAutoIncrease()) {
						if(field.getType().equals(String.class)&&SanDBHandler.getSecure()){
							contentValues.put(field.getName(), Encrypt.encrypt(Build.ID+"San99",String.valueOf(field.get(sanDbResult))));
						} else
						{contentValues.put(field.getName(), String.valueOf(field.get(sanDbResult)));}
					} else if (mapColumn.get(field.getName()).getAutoIncrease() && (int) field.get(sanDbResult) > 0) {
						contentValues.put(field.getName(), String.valueOf(field.get(sanDbResult)));
					}
				} catch (NullPointerException e){
					if(field.getType().equals(String.class)&&SanDBHandler.getSecure()){
						contentValues.put(field.getName(), Encrypt.encrypt(Build.ID+"San99",String.valueOf(field.get(sanDbResult))));
					} else
					{contentValues.put(field.getName(), String.valueOf(field.get(sanDbResult)));}
				}
			}
        }



        if(!update) {
				sqLiteDatabase.insert(tableName, null, contentValues);
		} else {
			sqLiteDatabase.update(tableName,contentValues,where,whereArgs);
		}
        sqLiteDatabase.close();
	}

	private String Upper(String str) {
		return str.substring(0,1).toUpperCase()+str.substring(1,str.length());
	}

	@SuppressWarnings({ "rawtypes" })
	 void delete(SanDbResult sanDbResult, String tableName,String where,String[] whereArgs) throws NoSuchFieldException, IllegalAccessException {
		SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Field[] fields = sanDbResult.getClass().getDeclaredFields();
		Table  table = SanDBHandler.mTable(sanDbResult.getClass());
		if(tableName==null)
		{
			tableName = table.getTbName();
		}

		if(where==null&&whereArgs==null) {

			for (Field field : fields) {
				field = sanDbResult.getClass().getDeclaredField(field.getName());
				field.setAccessible(true);
				if (field.getName().equals("id")) {
					sqLiteDatabase.delete(tableName, "id = ?", new String[]{String.valueOf(field.get(sanDbResult))});
					break;
				}
			}
		}else {
			deleteByQuery(tableName,where,whereArgs);
		}
		sqLiteDatabase.close();
	}

	
	 private void deleteByQuery(String tableName, String where, String[] whereArgs) throws NoSuchFieldException, IllegalAccessException {
		 SQLiteDatabase sqLiteDatabase = getWritableDatabase();
       if(tableName==null)
		{
			tableName = tables[0].getTbName();
		}
		sqLiteDatabase.delete(tableName, where, whereArgs);
       sqLiteDatabase.close();
	}




	 private void createFromAsset(Activity activity,String databaseName, Table tb) throws IOException {
		String DB_PATH = "/data/"+activity.getPackageName()+"/databases/";
		 dbName =databaseName;
		 tables = new Table[1];
		tables[0] = tb;
		tableHash = generateMaping();
		if(!exit(DB_PATH, databaseName))
		{
			createDir("/data"+DB_PATH);
			   InputStream localInputStream = activity.getAssets().open(databaseName);
	            
			   FileOutputStream localFileOutPutStream = new FileOutputStream("/data/"+DB_PATH + databaseName);
	            
	            byte[] arrayOfByte = new byte[1024];
	            while (true) {
	                int i = localInputStream.read(arrayOfByte);
	                if (i <= 0) {
	                    localFileOutPutStream.flush();
	                    localFileOutPutStream.close();
	                    localInputStream.close();
	                    return;
	                }
	                localFileOutPutStream.write(arrayOfByte, 0, i);
	            }

		}

	}



	private Boolean exit(String DB_PATH,String name){
		File dir = Environment.getDataDirectory();
		File file = new File(dir + DB_PATH, name);
		return file.exists();
	}
	
	private void createDir(String DB_PATH) {
        File localfile = new File(DB_PATH);
        if (!localfile.exists())
            localfile.mkdir();
    }

	 //<T extends SanDbResult<T>> List<T> getAllData(Class<?> mClass, String tbName, String o, String o1, int limit, int offset) {
	 <T extends SanDbResult<T>> List<T> getAllData(Class<?> mClass, String tb, String selection, String[] selectionArgs,int limit, int offset) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
	List<T> list= new ArrayList<>();

		 if(dbName!=null) {

			 if(tb==null)
			 {
				 tb = tables[0].getTbName();
			 }
			 String sql ="";
			 int count=0;
			 if(selection !=null && selectionArgs!=null){
				 StringTokenizer stk = new StringTokenizer(selection,"?");
				 StringBuilder sb = new StringBuilder();
				 while(stk.hasMoreElements())
				 {
					 sb.append(stk.nextToken()).append("'").append(selectionArgs[count]).append("'");
					 ++count;
				 }

				 sql = "SELECT * FROM "+tb+" WHERE "+sb.toString()+" LIMIT "+limit + " OFFSET "+offset;

			 } else {
				 sql = "SELECT * FROM "+tb+" LIMIT "+limit + " OFFSET "+offset;
			 }

			 if(SanDBHandler.getShowLog()){
				 System.out.println("SanSQL Log : "+sql);
			 }

			 SQLiteDatabase sqLiteDatabase = getReadableDatabase();
			 Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

			 try {
				 columns = tableHash.get(tb).getColumns();
			 } catch (NullPointerException npe)
			 {
				 Table table = SanDBHandler.mTable(mClass);
				 columns = table.getColumns();
			 }


			 //columns = new Column[cursor.getColumnCount()];
			 for(int i=0;i<cursor.getColumnCount();i++)
			 {
				 columns[i].setColumnName(cursor.getColumnName(i));
			 }

			 HashMap<String, Class> mapper = new HashMap<>();
			 List<String> listKey = new ArrayList<>();
			 Class cls = Class.forName(mClass.getName());

			 Method[] methods = cls.getDeclaredMethods();
			 for(Method method : methods) {
				 Class<?> type;
				 String methodName;
				 if(method.getName().startsWith("get")){
					 type = method.getReturnType();
					 methodName = "set"+method.getName().substring(3);
					 listKey.add(methodName);
					 mapper.put(methodName, type);
				 }

			 }

			 listKey = arrangeList(listKey);
			 cursor.moveToFirst();

			 do {
				 T entity	 = (T) cls.newInstance();
				 for(int i=0;i<listKey.size();i++)
				 {

					 String key = listKey.get(i);
					 Method method = cls.getDeclaredMethod(key, mapper.get(key));
					 try {
						 if (mapper.get(key).equals(int.class) || mapper.get(key).equals(Integer.class)) {
							 method.invoke(entity, cursor.getInt(i));
						 } else if (mapper.get(key).equals(String.class)) {
							 if(SanDBHandler.getSecure()){
								 method.invoke(entity, Encrypt.decrypt(Build.ID+"San99",cursor.getString(i)));
							 } else {
								 method.invoke(entity, cursor.getString(i)); }
						 } else if(mapper.get(key).equals(char.class))
						 {
							 method.invoke(entity, cursor.getString(i));
						 }

						 else if (mapper.get(key).equals(Blob.class)) {
							 method.invoke(entity, (Object) cursor.getBlob(i));
						 } else if (mapper.get(key).equals(Boolean.class) || mapper.get(key).equals(boolean.class)) {
							 method.invoke(entity, cursor.getInt(i) > 0);
						 } else if (mapper.get(key).equals(Bitmap.class)) {
							 method.invoke(entity, ByteToBitmap.getImage(cursor.getBlob(i)));
						 } else if (mapper.get(key).equals(Long.class) || mapper.get(key).equals(long.class)) {
							 method.invoke(entity, cursor.getLong(i));
						 } else if (mapper.get(key).equals(Double.class) || mapper.get(key).equals(double.class)) {
							 method.invoke(entity, cursor.getDouble(i));
						 } else if (mapper.get(key).equals(Float.class) || mapper.get(key).equals(float.class)) {
							 method.invoke(entity, cursor.getFloat(i));
						 } else {
							 method.invoke(entity, cursor.getString(i));
						 }
					 }catch (CursorIndexOutOfBoundsException ciob){ciob.printStackTrace(); return null;}
				 }
				 list.add(entity);
			 }while (cursor.moveToNext());
			 cursor.close();
			 sqLiteDatabase.close();
		 } else {
			 throw new SanDBHandlerErrorException("Database was not created!");
		 }

		 return list;
	}
}



