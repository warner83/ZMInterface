package it.zm.interfaces;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import de.jaret.util.date.Interval;
import de.jaret.util.date.IntervalImpl;
import de.jaret.util.date.JaretDate;
import de.jaret.util.ui.timebars.model.DefaultRowHeader;
import de.jaret.util.ui.timebars.model.DefaultTimeBarModel;
import de.jaret.util.ui.timebars.model.DefaultTimeBarRowModel;
import de.jaret.util.ui.timebars.model.TimeBarModel;
import de.jaret.util.ui.timebars.model.TimeBarRow;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;

public class EventsTimeBar {
	private List _headerList = new ArrayList();
	
	private DefaultTimeBarModel model;
	
	private JFrame f;
	
	public EventsTimeBar(){
        f = new JFrame("Time Bar");
        f.setSize(300, 330);
        f.getContentPane().setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTimeBarModel();
        
        TimeBarViewer tbv = new TimeBarViewer(model);
        
        f.getContentPane().add(tbv, BorderLayout.CENTER);
	}
	
	public void show(){
		f.setVisible(true);
	}

    public void addEvent(String monitorID, String duration, String eventID, String d) {
            DefaultRowHeader header = new DefaultRowHeader(monitorID);
            _headerList.add(header);
            DefaultTimeBarRowModel tbr = new DefaultTimeBarRowModel(header);
            JaretDate date = new JaretDate();
            
            int month = Integer.parseInt(d.substring(0, 2));                     
            int day = Integer.parseInt(d.substring(3, 5));
            int hours = Integer.parseInt(d.substring(6, 8));
            int minutes = Integer.parseInt(d.substring(9, 11));
            int seconds = Integer.parseInt(d.substring(12, 14));
            date.setDateTime(day, month, 2013, hours, minutes, seconds); 
                                    
            IntervalImpl interval = new IntervalImpl();
            interval.setBegin(date.copy());
            date.advanceSeconds(Double.parseDouble(duration));
            interval.setEnd(date.copy());
            tbr.addInterval(interval);
            model.addRow(tbr);
    }
}
