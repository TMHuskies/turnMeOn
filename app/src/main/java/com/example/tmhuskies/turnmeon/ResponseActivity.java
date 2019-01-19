package com.example.tmhuskies.turnmeon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ResponseActivity extends AppCompatActivity {

    private RecyclerView recyclerList;
    private RecyclerView.Adapter adapter;
    private ArrayList<ResponseOption> fileNames;
    private ArrayAdapter<ResponseOption> arrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.activity_response);
        listView = (ListView) findViewById(R.id.response_list);


        /*recyclerList = (RecyclerView)findViewById(R.id.response_list);
        recyclerList.setHasFixedSize(true);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));*/

        fileNames = new ArrayList<>();

        fileNames.add(new ResponseOption(AudioFiles.track1));
        fileNames.add(new ResponseOption(AudioFiles.track2));
        fileNames.add(new ResponseOption(AudioFiles.track3));
        fileNames.add(new ResponseOption(AudioFiles.track4));
        fileNames.add(new ResponseOption(AudioFiles.track5));

        arrayAdapter = new ResponseAdapter(fileNames, this);
        listView.setAdapter(arrayAdapter);

        //recyclerList.setAdapter(adapter);

        /*try {
            //URL url = getClass().getResource("audioFiles.xml");
            //File fXmlFile = new File(url.getPath());
            //Path path = Paths.get("C:\\Users\\Shanna\\Documents\\turnMeOn\\docs\\audioFiles.xml");

            File fXmlFile = new File("\\Users\\Shanna\\Documents\\turnMeOn\\docs\\audioFiles.xml");

            Toast.makeText(ResponseActivity.this, fXmlFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("file");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                Element element = (Element) node;
                String title = element.getElementsByTagName("file").item(0).getTextContent();
                fileNames.add(new ResponseOption(title));

                Toast.makeText(ResponseActivity.this, title, Toast.LENGTH_SHORT).show();
            }

            adapter = new ResponseAdapter(fileNames, this);

            recyclerList.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
