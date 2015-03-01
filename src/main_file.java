import cpabe.Cpabe;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class main_file{
	final static boolean DEBUG = true;

	static String pubfile = "file_path//pub_key";
	static String pubfile1 = "file_path//pub_key1";
	static String mskfile = "file_path//master_key";
	static String mskfile1 = "file_path//master_key1";
	static String prvfile = "file_path//prv_key";
	static String prvfile1 = "file_path//prv_key1";

	static String inputfile = "file_path//sample.txt";
	static String encfile = "file_path//input.pdf.cpabe";
	static String decfile = "file_path//input.pdf.new";
        static String dec_file = "file_path//dec.txt";

	static String[] attr = { "baf", "fim1", "foo" };
	static String[] another_attr = { "baf1", "fim1", "foo" };
	static String policy = "foo bar fim 2of3 baf 1of2";

	static String student_attr = "objectClass:inetOrgPerson "
		+ "objectClass:organizationalPerson sn:student2 cn:student2 "
		+ "uid:student2 userPassword:student2 ou:idp o:computer "
		+ "mail:student2@sdu.edu.cn title:student";
        static String sample_attr = "objectClass:inet Org Person "
		+ "objectClass:organizational Person sn:student2 cn:student2 "
		+ "uid:student2 userPassword:student2 ou:idp o:computer "
		+ "mail:student2@sdu.edu.cn title:student";

	static String student_policy = "sn:student2 cn:student2 "
		+ "uid:student2 2of3";
/*
        static String student_attr = "objectClass:inetOrgPerson objectClass:organizationalPerson "
                    	+ "sn:student2 cn:student2 uid:student2 userPassword:student2 "
			+ "ou:idp o:computer mail:student2@sdu.edu.cn title:student";

	static String student_policy = "sn:student2 cn:student2 uid:student2 3of3";*/
	public static void main(String[] args) throws Exception {
		String attr_str;
		// policy = student_policy;
		// attr_str = array2Str(student_attr);
                String sample;
                sample=sample_attr;
		attr_str = student_attr;
                System.out.println("sample = " + sample);
                System.out.println("attr_str = " + attr_str);
		policy = student_policy;
                System.out.println("policy = " + policy);

		Cpabe test = new Cpabe();
                Cpabe test1 = new Cpabe();
		println("//start to setup");
		test.setup(pubfile, mskfile);
                test1.setup(pubfile1,mskfile1);
		println("//end to setup");
        /*        BufferedReader br=null,br1=null;
              br= new BufferedReader(new FileReader(pubfile));
              br1= new BufferedReader(new FileReader(pubfile1));
              while(br.ready())
              {
                  System.out.println(br.readLine());
              }
              while(br1.ready())
              {
                  System.out.println(br1.readLine());
              }
                File fp=new File(pubfile);
                System.out.println();*/
		println("//start to keygen");
		test.keyGeneration(pubfile, prvfile, mskfile, attr_str);
                //test1.keygen(pubfile1,prvfile1,mskfile1,sample);
		println("//end to keygen");

		println("//start to enc");
		test.encryption(pubfile, policy, inputfile, encfile);
		println("//end to enc");
                if(prvfile.contentEquals(prvfile1))
                {
                    System.out.println("Fuck you");
                }
                else
                {
                    System.out.println("shit");
                }
		println("//start to dec");
		test.decryption(pubfile, prvfile, encfile, decfile);
                //test1.dec(pubfile1,prvfile1,encfile,dec_file);
		println("//end to dec");
	}

	/* connect element of array with blank */
	public static String array2Str(String[] arr) {
		int len = arr.length;
		String str = arr[0];

		for (int i = 1; i < len; i++) {
			str += " ";
			str += arr[i];
		}

		return str;
	}

	private static void println(Object o) {
		if (DEBUG)
			System.out.println(o);
	}
}
