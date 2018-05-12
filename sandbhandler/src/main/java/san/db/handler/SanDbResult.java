package san.db.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SanDbResult<T> {
	
	@Deprecated
	public static <T extends SanDbResult<T>> List<T> getAllData(Class<?> mClass,String tableName) {
		try {
			return  CreateDatabase.getInstance().getAllData(mClass, tableName, null,null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Deprecated
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends SanDbResult> T getDataById(Class<?> mClass,int id,String tableName){


		try {
			List<T> list =CreateDatabase.getInstance().getAllData(mClass, tableName, "id=?",new String[]{String.valueOf(id)});
			//CreateDatabase.getInstance().getDataById(mClass,false,tableName,null,"id",new String[]{String.valueOf(id)},null,null,null,null);
			return list.get(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Deprecated
	public void insert(String tableName) {

		try {
			CreateDatabase.getInstance().add(this,tableName,false,null,null);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}


	public static <T extends SanDbResult<T>> List<T> getAllData(Class<?> mClass) {
		Table table = SanDBHandler.mTable(mClass);
		try {
			return CreateDatabase.getInstance().getAllData(mClass, table.getTbName(), null,null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends SanDbResult> T getDataById(Class<?> mClass,int id){
		Table table = SanDBHandler.mTable(mClass);
		try {
			List<T> list =CreateDatabase.getInstance().getAllData(mClass, table.getTbName(), "id=?",new String[]{String.valueOf(id)});
			return list.get(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void insert() {
		try {
			CreateDatabase.getInstance().add(this, null,false,null,null);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static <T extends SanDbResult<T>> List<T> getAllDataByQuery(Class<?> mClass,String whereQuery,String[] whereArgs){
		Table table = SanDBHandler.mTable(mClass);
		try {
			return CreateDatabase.getInstance().getAllData(mClass,table.getTbName(),whereQuery,whereArgs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

@Deprecated
	public static <T extends SanDbResult<T>> List<T> getAllDataByQuery(Class<?> mClass,String tableName,String whereQuery,String[] whereArgs){
		try {
			return CreateDatabase.getInstance().getAllData(mClass,tableName,whereQuery,whereArgs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends SanDbResult> T getDataByQuery(Class<?> mClass,String whereQuery,String[] whereArgs){
		Table table = SanDBHandler.mTable(mClass);
		try {
			List<T> list =CreateDatabase.getInstance().getAllData(mClass, table.getTbName(),whereQuery,whereArgs);
			return list.get(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Deprecated
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends SanDbResult> T getDataByQuery(Class<?> mClass,String tableName,String whereQuery,String[] whereArgs){

		try {
			List<T> list =CreateDatabase.getInstance().getAllData(mClass, tableName,whereQuery,whereArgs);
			return list.get(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
    }



	public void update(String tableName,String where,String[] whereArgs) {
		try {
			CreateDatabase.getInstance().add(this,tableName,true,where,whereArgs);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(){
		try {
			CreateDatabase.getInstance().delete(this, null, null, null);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	@Deprecated
	public void delete(String tableName){
		try {
			CreateDatabase.getInstance().delete(this, tableName, null, null);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	@Deprecated
	public  void delete(String tableName,String where,String[] whereArgs) {
		try {
			CreateDatabase.getInstance().delete(this,tableName, where, whereArgs);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public  void delete(String where,String[] whereArgs) {
		try {
			CreateDatabase.getInstance().delete(this,null, where, whereArgs);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
