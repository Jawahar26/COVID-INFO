import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Iterator;

public class sub_app_window {
   String District[]={};
   static JFrame f;
   static JComboBox sp,dp;
    static int data_confirmed,data_deceased,data_recovered ,data_confirmed_district, data_deceased_district, data_recovered_district;

    sub_app_window(){
        f= new JFrame("COVID-INFO");
        String State[]={};
        String District[]={};


        String jsonString = callURL("https://api.covid19india.org/state_district_wise.json");


        JPanel title=new JPanel();
        title.setBounds(300,50,900,100);

        JLabel text=new JLabel("welcome to COVID-INFO");



        //System.out.println("\n\njsonString: " + jsonString);
        //---------------------------get state name-------------------------------------
        try {
            JSONObject obj_state = new JSONObject(jsonString);
            State = new String[obj_state.getString("State Unassigned").length()];
            int i =1;
            Iterator iterator = obj_state.keys();
            State[0]="Select State";
            String key = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                State[i] = key;
                i++;
               //System.out.println(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*-----------combo-------*/
        //State Combobox
        JPanel statepanel=new JPanel();

        /* ----------------------- State Data List LAbels -------------------*/
        JLabel StateHeading = new JLabel();
        statepanel.setBounds(100,200,600,80);


        JLabel b1=new JLabel();
        b1.setBounds(50,100,80,30);
        b1.setBackground(Color.yellow);




        JLabel b3=new JLabel();
        b3.setBounds(50,100,80,30);
        // b1.setBackground(Color.yellow);


        JLabel b5=new JLabel();
        b5.setBounds(50,100,80,30);
        /* ----------------------- State Data List LAbels -------------------*/


        /* ----------------------- District  Data List LAbels -------------------*/

        JLabel DistrictHeading = new JLabel();
        statepanel.setBounds(100,200,600,80);


        JLabel bd1=new JLabel();
        bd1.setBounds(50,100,80,30);
        //bd1.setBackground(Color.yellow);




        JLabel bd3=new JLabel();
        bd3.setBounds(50,100,80,30);
        // b1.setBackground(Color.yellow);


        JLabel bd5=new JLabel();
        bd5.setBounds(50,100,80,30);


        /* ----------------------- District Data List LAbels -------------------*/


        sp=new JComboBox(State);
        sp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //sub_app_window c_obj = new sub_app_window();
                display_district(sp.getSelectedItem().toString(),jsonString);

                data_confirmed = get_positive_cases(sp.getSelectedItem().toString(),jsonString);
                data_recovered = get_recovered_cases(sp.getSelectedItem().toString(),jsonString);
                data_deceased = get_deceased_cases(sp.getSelectedItem().toString(),jsonString);
                b1.setText("Confirmed Cases:  "+data_confirmed);
                b3.setText("Deceased Cases: "+data_deceased);
                b5.setText("Recovered Cases: "+data_recovered);
            }
        });

        statepanel.add(sp);


        //District combobox
        JPanel dtpanel=new JPanel();
        dtpanel.setBounds(600,200,900,80);

        dp=new JComboBox();
        dp.addItem("Select District");
        dp.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    //some stuff
                    //sub_app_window c_obj = new sub_app_window();

                    if(dp.getSelectedItem().toString() != "") {
                        data_confirmed_district = get_positive_cases_district(sp.getSelectedItem().toString(), dp.getSelectedItem().toString(), jsonString);
                        data_recovered_district = get_recovered_cases_district(sp.getSelectedItem().toString(), dp.getSelectedItem().toString(), jsonString);
                        data_deceased_district = get_deceased_cases_district(sp.getSelectedItem().toString(), dp.getSelectedItem().toString(), jsonString);
                        bd1.setText("Confirmed Cases:  " + data_confirmed_district);
                        bd3.setText("Deceased Cases: " + data_deceased_district);
                        bd5.setText("Recovered Cases: " + data_recovered_district);
                    }
                }
            }
        });

        dtpanel.add(dp);
        /*-----------combo-------*/

        /*---- Data panel state-----------------*/

        JPanel datapanel=new JPanel();
        datapanel.setSize(5,30);
        datapanel.setBounds(300,300,200,200);
       // datapanel.setBackground(Color.gray);


        StateHeading.setFont(new Font("Times New Roman", Font.BOLD, 18));


        // b1.setBackground(Color.yellow);

        // b2.setBackground(Color.green);
        /*---- Data panel state-----------------*/

        /*---- Data panel district----------------*/

        JPanel datapanel_district=new JPanel();
        datapanel_district.setSize(5,30);
        datapanel_district.setBounds(950,300,200,200);
         //datapanel_district.setBackground(Color.gray);


        //StateHeading.setFont(new Font("Times New Roman", Font.BOLD, 18));


        // b1.setBackground(Color.yellow);

        // b2.setBackground(Color.green);
        /*---- Data panel district-----------------*/




        title.add(text);
        datapanel.setLayout(new BoxLayout(datapanel, BoxLayout.PAGE_AXIS));
        datapanel.add(StateHeading);
        datapanel.add(b1);

        datapanel.add(b3);

        datapanel.add(b5);

        datapanel_district.add(bd1);
        datapanel_district.add(bd3);
        datapanel_district.add(bd5);



        f.add(BorderLayout.CENTER,title);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);


        f.add(statepanel);
        f.add(dtpanel);

        f.add(datapanel);
        f.add(datapanel_district);
        f.setLayout(null);
        f.setVisible(true);

    }

/*--------------------------To display Districts based on the state chosen ---------------*/
    public void display_district(String statename, String jsonString){
        //-------------------get district name----------------------

        try {

            JSONObject obj_state = new JSONObject(jsonString);
            JSONObject obj_district_key = new JSONObject(obj_state.getString(statename));

            // System.out.println(obj_district_key.getString("districtData"));

            JSONObject obj_district= new JSONObject(obj_district_key.getString("districtData"));
            int i=1;
            District=new String[obj_district_key.getString("districtData").length()];
            Iterator iterator = obj_district.keys();
            dp.removeAllItems();
            //District[0]="Select District";
            dp.addItem("Select District");

            String key = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                District[i]=key;
                i++;
                dp.addItem(key);
               // System.out.println(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

 /*---------------------State wise data------------------------*/
    public int get_positive_cases(String state,String jsonString){
        int confirmed = 0;
        try {
            JSONObject obj_state = new JSONObject(jsonString);
            JSONObject obj_district_key = new JSONObject(obj_state.getString(state));
            JSONObject obj_district= new JSONObject(obj_district_key.getString("districtData"));
            JSONObject obj_district_values;

            Iterator iterator = obj_district.keys();
            String key = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                obj_district_values = new JSONObject(obj_district.getString(key));
                confirmed = confirmed + Integer.parseInt(obj_district_values.get("confirmed").toString());

            }
            System.out.println(confirmed);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return confirmed;
    }

    public int get_recovered_cases(String state,String jsonString){
        int recovered = 0;
        try {
            JSONObject obj_state = new JSONObject(jsonString);
            JSONObject obj_district_key = new JSONObject(obj_state.getString(state));
            JSONObject obj_district= new JSONObject(obj_district_key.getString("districtData"));
            JSONObject obj_district_values;

            Iterator iterator = obj_district.keys();
            String key = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                obj_district_values = new JSONObject(obj_district.getString(key));
                recovered = recovered + Integer.parseInt(obj_district_values.get("recovered").toString());

            }
            //System.out.println(confirmed);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recovered;
    }
    public int get_deceased_cases(String state,String jsonString){
        int deceased = 0;
        try {
            JSONObject obj_state = new JSONObject(jsonString);
            JSONObject obj_district_key = new JSONObject(obj_state.getString(state));
            JSONObject obj_district= new JSONObject(obj_district_key.getString("districtData"));
            JSONObject obj_district_values;

            Iterator iterator = obj_district.keys();
            String key = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                obj_district_values = new JSONObject(obj_district.getString(key));
                deceased = deceased + Integer.parseInt(obj_district_values.get("deceased").toString());

            }
            System.out.println(deceased);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deceased;
    }


    /*----------------------------------District Cases Data Retrival --------------------*/
    public int get_positive_cases_district(String state,String district,String jsonString){
        int confirmed = 0;
        if(district != "Select District") {
            try {
                JSONObject obj_state = new JSONObject(jsonString);
                JSONObject obj_district_key = new JSONObject(obj_state.getString(state));
                JSONObject obj_district = new JSONObject(obj_district_key.getString("districtData"));
                JSONObject obj_district_values = new JSONObject(obj_district.getString(district));

                confirmed = Integer.parseInt(obj_district_values.getString("confirmed"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return confirmed;
    }

    public int get_recovered_cases_district(String state,String district,String jsonString){
        int recovered = 0;
        if(district != "Select District") {
            try {
                JSONObject obj_state = new JSONObject(jsonString);
                JSONObject obj_district_key = new JSONObject(obj_state.getString(state));
                JSONObject obj_district = new JSONObject(obj_district_key.getString("districtData"));
                JSONObject obj_district_values = new JSONObject(obj_district.getString(district));

                recovered = Integer.parseInt(obj_district_values.getString("recovered"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return recovered;
    }

    public int get_deceased_cases_district(String state,String district,String jsonString){
        int deceased = 0;
        if(district != "Select District") {
            try {
                JSONObject obj_state = new JSONObject(jsonString);
                JSONObject obj_district_key = new JSONObject(obj_state.getString(state));
                JSONObject obj_district = new JSONObject(obj_district_key.getString("districtData"));
                JSONObject obj_district_values = new JSONObject(obj_district.getString(district));

                deceased = Integer.parseInt(obj_district_values.getString("deceased"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return deceased;
    }
    public static String callURL(String myURL) {
        System.out.println("Requested URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        }

        return sb.toString();
    }
    public static void main(String[] args){
        new sub_app_window();
    }
}
