# SanDatabaseHandler

This library is design to handle the SQL Database easily in android.

ဒီ Library လေးကတော့ Database နဲ့ အမြဲထိတွေ့နေရတဲ့ Developer တွေ၊ SQL Database ကို သုံးချင်ပေမဲ့ မသုံးတတ်သေးတဲ့ Beginner တွေ၊ Database တွေ ခဏခဏ တည်ဆောက်ရ၊ Query တွေ အထပ်ထပ်ဆောက်ရ အလုပ်ရှုပ် အချိန်ကုန်ခံရတာတွေ သက်သာစေဖို့ ရည်ရွယ်ပြီး ရေးထားတာပါ။ ဒီ Library မှာ ပါဝင်တဲ့ Feature တွေကတော့ -

	Database တည်ဆောက်ရလွယ်ကူခြင်း
	
	Table များမြန်ဆန်စွာဆောက်နိုင်ခြင်း
	
	လျှင်မြန်စွာ Query လုပ်နိုင်ခြင်း
	
	Data များ encrypt,decrypt လုပ်နိုင်ခြင်း
	
	Database အား SD card သို့ export လုပ်နိုင်ခြင်း
	
	Database,Table name နှင့် Columns များကို Object Class ဖြင့် တည်ဆောက်နိုင်ခြင်း
	
	Multi table များ ကို တပြိုင်တည်း query လုပ်နိုင်ခြင်း စသည့် အားသာချက်များ ရရှိနိုင်ပါတယ်။
	
	Bitmap column တည်ဆောက်ခြင်းဖြင့် လွယ်ကူစွာ image များ သိမ်းနိုင် query လုပ်နိုင်ခြင်း။

Library ေလးကိုအသံုးျပဳမယ္ဆိုရင္ေတာ့

root build.gradle ထဲက allproject ထဲမွာ ေအာက္ပါအတုိင္း maven url ေလး ထည့္ေပးရပါမယ္။

	allprojects {
 		repositories {
 		....
		maven { url 'https://jitpack.io' }
		}
	}

app gradle ထဲက dependencies ထဲမွာ ေတာ့ ေအာက္ပါအတုိင္း ထည့္ရပါမယ္။

	dependencies {
		compile 'com.github.SanKoKo:SanDatabaseHandler:1.0'
	}


Sync Now ကိုႏွိပ္ၿပီး build လုပ္ပါ။ build လုပ္တာၿပီးသြားရင္ေတာ့ library ကို အသံုးျပဳႏိုင္ပါၿပီး။


	SanDBHandler sanDBHandler = new SanDBHandler(this);

Database တည္ေဆာက္ရာမွာ ေအာ္ကပါအတိုင္း (၃)မ်ဳိးတည္ေဆာက္ႏိုင္ပါတယ္

# ၁။ Object Class ဖြင့် Database တည်ဆောက်ခြင်း

အောက်ပါအတိုင်း ရေး ရန်သာလိုပါသည်။
  
sanDBHandler.createDatabaseByClass("HERO",Note.class,Book.class);

အထက်ပါ ဥပမာအရ Note နှင့် Book table နှစ်ခု Hero ဟုခေါ်သော database file အတွင်းတည်ဆောက်သွားမည်ဖြစ်သည်။

# ၂။  Assest folder ထဲမွ database အားအသံုးျပဳ ျခင္း
createDatabaseFromAsset(Activity activity, String databaseName, Class<?> defaultTableClass) ဟုခေါ်ရန်သာ လိုပါသည်။

# ၃။  အေသးစိတ္ေရး၍ တည္ေဆာက္ျခင္း

	sanDBHandler.createDatabase(new Database() { @Override public Table[] addTable() {
	//Column တွေကို ဒီနေရာရေးရပါမယ်။ 
	//အရေးကြီးဆုံးအချက်က “id” Column မဖြစ်မနေထည့်ပေးရပါမယ်။ 
	//Column တွေကို attribute ထည့်မယ်ဆိုရင် columns[0] မှာ နမူနာ ပြထားတဲ့အတိုင်း .set ခံပြီး ထည့်သွားရပါမယ်။
	
	Column[] columns = new Column[3];
	
	columns[0] = new Column("id",int.class).setAutoIncrement().setNotNull().setPrimary();
	
	columns[1] = new Column("title",String.class);
	
	columns[2] = new Column("price",String.class);


	Table table = new Table("BookTable",columns);

	return new Table[]{table}; //တကယ်လို့ table တွေအများကြီးဖြစ်မယ်ဆိုရင် ဒီနေရာမှာ Table array type return ပြန်ရမှာပါ
	}
	
	@Override public String addDatabaseName() { return "Book"; //Database name ဖြစ်ပါတယ်။ } });

# CURD လုပ်ခြင်း 

CURD လုပ်မည့် Object class ထဲက field တွေအားလုံး Table ထဲက column name နဲ့တူရပါမယ်။ ပြီးရင်တော့ SanDbResult ကို extends လုပ်ရပါမယ်။ အပေါ်က table အတွက် ဆိုပါစို့၊ အောက်ပါအတိုင်း ရေးရပါမယ်။
	
	public class Book extends SanDbResult<Book> {

	int id;
	String title;
	String price;

	public Book() {
	}

	public Book(int id, String title, String price) {
	    this.id = id;
	    this.title = title;
	    this.price = price;
	}

	public Book(String title, String price) {
	    this.id = id;
	    this.title = title;
	    this.price = price;
	}

	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}

	public String getTitle() {
	    return title;
	}

	public void setTitle(String title) {
	    this.title = title;
	}

	public String getPrice() {
	    return price;
	}

	public void setPrice(String price) {
	    this.price = price;
	}
	}
extends လုပ်ထားတာတွေ့ရပါမယ်။ empty constructor မဖြစ်မနေတည်ဆောက်ပေးရပါမယ်။

# Insert
database ထဲ insert လုပ်မယ်ဆိုရင် အောက်ပါအတိုင်း ရေးရန်သာလိုပါတယ်။ id value ကို မထည့်ထားရခြင်းကတော့ Column ကို autoIncrease ပေးထားလို့ပါ။
	
	Book book = new Book("bnew","$10");
	
	book.insert();

table တွေ အများကြီးတည်ဆောက်ထားခဲ့မယ်ဆိုရင်တော့ insert() ထဲမှာ table name ထည့်ပေးရပါမယ်။ ဥပမာ - book.insert(“Book”); 


# Update
Update လုပ်ရန် အောက်ပါအတိုင်း ရေးရန်သာလိုပါတယ်။ ဥပမာ ထဲမှာဆိုရင် id =1 ဖြစ်တဲ့ row ကို update လုပ်မှာပါ။
	
	Book b = new Book("BLOOK","$1234500");

	b.update(null,"id = ?",new String[]{"1"});

null ဆိုတာက table name ကိုဆိုလိုတာပါ။ Table တစ်ခုတည်းရှိတယ်ဆိုရင် null လို့ ရေးလို့ရပါတယ်။ Query ကိုတော့ မိမိစိတ်ကြိုက်ရေးနိုင်ပါတယ်။

# Select 

id နဲ့ select လုပ်လိုတယ်ဆိုရင် အောက်ပါအတိုင်းရေးရန်လိုပါတယ်။ Query ပြုလုပ်ရမှာလည်း Table တစ်ခုထက်မက ရှိနေမယ်ဆိုရင်တော့ table name တွေ ထည့်ပေးဖို့လိုပါတယ်။

	Book book3 = Book.getDataById(Book.class,1);

data အားလုံး လိုချင်တာဆိုရင်တော့

	List<Book> list2 = Book.getAllData(Book.class);

ကိုယ်ပိုင် query တည်ဆောက်လိုရင်တော့ 

	Book book = Book.getDataByQuery(Book.class,null,"id >?",new String[]{"2"});

# Delete
	Book b = Book.getDataById(Book.class,1);
	b.delete();

Query ဖြင့် delete လုပ်ခြင်း

	Book.deleteByQuery(null,"id = ?",new String[]{"1"}); //null သည် table name ဖြစ်ပါသည်။


  
Column attribute ထည့်ချင်တယ်ဆိုရင်တော့
  
Note class ကို ဥပမာ ပြပါမည်။
  
	import san.db.handler.SetColumnAttr;

	public class Note extends SanDbResult<Note> {

	@SetColumnAttr(setPrimary = true,setAutoIncrement = true) //setColumnAttr အတွင်း ထည့်ရန်သာလိုပါသည်။
	int id;
	String abc;
	String clean;
	Bitmap pic;



	public Note() {
	}

	public Note(int id, String abc, String clean) {
	    this.id = id;
	    this.abc = abc;
	    this.clean = clean;
	}
	public Note( String abc, String clean) {
	    this.abc = abc;
	    this.clean = clean;
	}


	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}

	public String getAbc() {
	    return abc;
	}

	public void setAbc(String abc) {
	    this.abc = abc;
	}

	public String getClean() {
	    return clean;
	}

	public void setClean(String clean) {
	    this.clean = clean;
	}

	public Bitmap getPic() {
	    return pic;
	}

	public void setPic(Bitmap pic) {
	this.pic = pic;
	}
	}

ဒေတာများအား encrypt ပြုလုပ် သိမ်းဆည်းလိုပါက

	sanDBHandler.setSecure(true);

တစ်ကြောင်းသာ ထည့်ရန်လိုပါသည်။ String များအားလုံးကို encrypt ၊ decrypt ပြုလုပ်ပေးသွားပါမည်။


မိမိ အသုံးပြုလိုသော Feature များအား အကြံအဉာဏ်ပေးနိုင်ပါသည်။ Library အသုံးပြုပါက ကျေးဇူးပြု၍ credit ပေးစေလိုပါသည်။
