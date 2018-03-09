package san.db.handler;

class Encrypt {

	   public static String encrypt(String secretKey, String plainText){
		   	   String en="";
		   	   char[] plainTxT;
			   char[] aa = secretKey.toCharArray();
			   if(plainText.length()>369)
			   {
				   for(int i=0;i<plainText.length();i+=369){
					   int maxLength = i+369;
					   if(maxLength>plainText.length()){
						   maxLength = maxLength - plainText.length();
					   }
				plainTxT = plainText.substring(i, maxLength).toCharArray();
				en += encryptMethod(plainTxT,aa);
				   }
			   } else {
			    plainTxT = plainText.toCharArray();
			    en +=encryptMethod(plainTxT, aa);
			   }
			  
		   
	              return en;
	   }
	   
	   static String encryptMethod(char[] txt,char[] aa)
	   {
		   String en="";
		   int loopLength;
		   	if(txt.length<aa.length)
		   	{
		   		loopLength = txt.length;
		   	} else {
		   		loopLength = aa.length;
		   	}
		   for(int i=0;i<txt.length;i+=loopLength)
		   {
			   for(int j=0;j<loopLength;j++)
			   {
				   try{
					   int a = txt[i+j]*aa[j];
					  en +=(char)a;
				   }catch (IndexOutOfBoundsException e) {
					   		return en;
				}

			   }
			   
			   
		   }
		   
		   return en;
	   }
	   
	   static String decryptMethod(char[] txt,char[] aa)
	   {
		   String en="";
		   int loopLength;
		   	if(txt.length<aa.length)
		   	{
		   		loopLength = txt.length;
		   	} else {
		   		loopLength = aa.length;
		   	}
		   for(int i=0;i<txt.length;i+=loopLength)
		   {
			   for(int j=0;j<loopLength;j++)
			   {
				   try{
				   int a = txt[i+j]/aa[j];
				   en +=(char)a;
				   } catch (IndexOutOfBoundsException e) {
					   return en;
				   }
			   }
			   
			   
		   }
		   
		   return en;
	   }

	   public static String decrypt(String secretKey, String encryptedText){
		   String en="";
	   	   char[] plainTxT;
		   char[] aa = secretKey.toCharArray();
		   if(encryptedText.length()>369)
		   {
			   for(int i=0;i<encryptedText.length();i+=369){
				   int maxLength = i+369;
				   if(maxLength>encryptedText.length()){
					   maxLength = maxLength - encryptedText.length()+1;
				   }
			plainTxT = encryptedText.substring(i, maxLength).toCharArray();
			en += decryptMethod(plainTxT,aa);
			   }
		   } else {
		    plainTxT = encryptedText.toCharArray();
		    en += decryptMethod(plainTxT,aa);
		   }
		  
	   
	          return en;
	   }    
	    
}
