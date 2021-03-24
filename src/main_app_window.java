 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;

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
 import javax.swing.*;
    public class main_app_window {
       // private static country_list CrunchyJSON;

        main_app_window() {
            // String jsonString;
            //String[] country = new String[0];
            JFrame f = new JFrame("COVID-INFO");
            /*--------------Title--------------*/
            JPanel title = new JPanel();
            title.setBounds(300, 50, 900, 100);

            JLabel text = new JLabel("welcome to COVID-INFO");


            /*-----------combo-------*/
            JPanel combopanel = new JPanel();
            combopanel.setBounds(300, 200, 900, 80);
            String country[] = {};
            JComboBox cb;
            JLabel b1 = new JLabel();
            b1.setBounds(50, 100, 50, 30);

            JLabel b2 = new JLabel();
            b2.setBounds(50, 150, 50, 30);

            JLabel b3 = new JLabel();
            b3.setBounds(50, 200, 80, 30);

            JLabel b4 = new JLabel();
            b4.setBounds(50, 250, 80, 30);

            JLabel b5 = new JLabel();
            b5.setBounds(50, 300, 80, 30);


            JLabel b6 = new JLabel();
            b6.setBounds(50, 350, 80, 30);

            JLabel b7 = new JLabel();
            b7.setBounds(50, 400, 80, 30);

            JLabel b8 = new JLabel();
            b8.setBounds(50, 450, 80, 30);


            JButton m1 = new JButton("More information");
            m1.setBounds(700, 300, 150, 30);
            m1.setVisible(false);
            m1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    //f.dispose();
                   new sub_app_window();
                }
            });
            // m1.setEnabled(false);

            JTextArea texta = new JTextArea("", 40, 20);

            texta.setEditable(false);
            /*-----------combo-------*/


            String jsonString = callURL("https://coronavirus-19-api.herokuapp.com/countries");


            try {
                // String jsonString = new String[0];
                JSONArray jsonArray = new JSONArray(jsonString);

                //System.out.println("\n\njsonArray: " + jsonArray);


                int count = jsonArray.length(); // get totalCount of all jsonObjects
                country = new String[count + 1]; // create array

                country[0] = "Select Country";
                for (int i = 0; i < count; i++) {   // iterate through jsonArray
                    JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                    country[i + 1] = (String) jsonObject.get("country");
                }
                cb = new JComboBox(country);
                combopanel.add(cb);
                cb.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            //some stuff
                          //  sub_app_window c_obj = new sub_app_window();
                            for (int i = 0; i < count; i++) {   // iterate through jsonArray
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if (cb.getSelectedItem().toString() == jsonObject.get("country")) {
                                        System.out.println();
                                        if (cb.getSelectedItem().equals("India")) {
                                            m1.setVisible(true);
                                        } else {
                                            m1.setVisible(false);
                                        }
                                        System.out.println(i);
                                        b1.setText("Cases :" + " " + jsonObject.get("cases").toString());
                                        b2.setText("todayCases :" + " " + jsonObject.get("todayCases").toString());
                                        b3.setText("deaths :" + " " + jsonObject.get("deaths").toString());
                                        b4.setText("todayDeaths :" + " " + jsonObject.get("todayDeaths").toString());
                                        b5.setText("recovered :" + " " + jsonObject.get("recovered").toString());
                                        b6.setText("active :" + " " + jsonObject.get("active").toString());
                                        b7.setText("critical :" + " " + jsonObject.get("critical").toString());
                                        b8.setText("totalTests :" + " " + jsonObject.get("totalTests").toString());
                                        break;
                                    }
                                } catch (JSONException jsonException) {
                                    jsonException.printStackTrace();
                                }


                            }
                        }
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*--------------------Get feed--------------*/
            String jsonString1 = callURL("https://newsapi.org/v2/top-headlines?country=in&apiKey=836286f1d1ea4c0bbf2759f7d92b8315");
            //  System.out.println("\n\njsonString: " + jsonString);

// Replace this try catch block for all below subsequent examples
            try {
                JSONObject obj_state = new JSONObject(jsonString1);
                for (int i = 0; i < obj_state.getJSONArray("articles").length(); i++) {
                    texta.append("TITLE:" + obj_state.getJSONArray("articles").getJSONObject(i).getString("title") + "\n");
                    texta.append("\n");
                    texta.append("DESCRIPTION:" + obj_state.getJSONArray("articles").getJSONObject(i).getString("description") + "\n");
                    texta.append("\n");

                    texta.append("URL:" + obj_state.getJSONArray("articles").getJSONObject(i).getString("url") + "\n");
                    texta.append("\n");
                    texta.append("\n");
                    texta.append("\n");
                    texta.append("\n");
                }
                texta.setLineWrap(true);
//
//           int count = jsonArray.length(); // get totalCount of all jsonObjects
//            for (int i = 0; i < count; i++) {   // iterate through jsonArray
//                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
//              System.out.println(jsonObject.get("country"));
//            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*---------feed----------*/
            JPanel feedpanel = new JPanel();
            feedpanel.setSize(5, 30);
            feedpanel.setBounds(1240, 75, 300, 900);
            // feedpanel.setBackground(Color.gray);
            JLabel newslabel = new JLabel("NEWS FEED");
            newslabel.setBounds(1350, 0, 300, 100);

            //texta.setCaretPosition(texta.getDocument().getLength() - 1);
            JScrollPane scroll = new JScrollPane(texta);

            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            /*---------feed----------*/
            /*---- Data-----------------*/

            JPanel datapanel = new JPanel();
            datapanel.setSize(4, 30);
            datapanel.setBounds(300, 300, 160, 200);

            /*---- Data-----------------*/

            /*----------button--------*/
//            JPanel morepanel=new JPanel();
//
//            morepanel.setSize(5,30);
//            morepanel.setBounds(700,300,125,50);
            // morepanel.setBackground(Color.gray);


            //m1.setBackground(Color.orange);

            /*----------button--------*/

            title.add(text);
            datapanel.add(b1);
            datapanel.add(b2);
            datapanel.add(b3);
            datapanel.add(b4);
            datapanel.add(b5);
            datapanel.add(b6);
            datapanel.add(b7);
            datapanel.add(b8);
            // feedpanel.add(BorderLayout.NORTH,newslabel);
            feedpanel.add(scroll);
            //  morepanel.add(m1);


            f.add(BorderLayout.CENTER, title);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screenSize.width, screenSize.height);

            // f.add(BorderLayout.CENTER,title);
            f.add(combopanel);
            f.add(BorderLayout.CENTER, newslabel);
            f.add(BorderLayout.CENTER, feedpanel);
            //f.add(scroll);
            // f.add(morepanel);
            f.add(m1);
            f.add(datapanel);
            // f.setSize(400,400);
            f.setLayout(null);
            f.setVisible(true);
        }

//        public static void createFrame()
//        {
//            EventQueue.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                new sub_app_window();
//                }
//            });
//        }
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
        public static void main(String args[])
        {
            new main_app_window();
        }
    }

