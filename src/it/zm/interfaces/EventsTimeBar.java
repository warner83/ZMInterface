package it.zm.interfaces;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import de.jaret.util.date.Interval;
import de.jaret.util.date.IntervalImpl;
import de.jaret.util.date.JaretDate;
import de.jaret.util.ui.timebars.TimeBarViewerInterface;
import de.jaret.util.ui.timebars.mod.DefaultIntervalModificator;
import de.jaret.util.ui.timebars.model.DefaultRowHeader;
import de.jaret.util.ui.timebars.model.DefaultTimeBarModel;
import de.jaret.util.ui.timebars.model.DefaultTimeBarRowModel;
import de.jaret.util.ui.timebars.model.TimeBarModel;
import de.jaret.util.ui.timebars.model.TimeBarRow;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.renderer.DefaultGapRenderer;
import de.jaret.util.ui.timebars.swing.renderer.DefaultTimeScaleRenderer;

public class EventsTimeBar {
	private List _headerList = new ArrayList();
	
	private DefaultTimeBarModel model;
	
	private TimeBarViewer tbv;
	
	private JFrame f;
	
	public EventsTimeBar(){
        f = new JFrame("Time Bar");
        f.setSize(300, 330);
        f.getContentPane().setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTimeBarModel();
                
        tbv = new TimeBarViewer(model);
        
        tbv.addIntervalModificator(new DefaultIntervalModificator());
        
        tbv.setAdjustMinMaxDatesByModel(false);
        
        tbv.setPixelPerSecond(0.05);
        //tbv.setDrawRowGrid(true);
                  
        //tbv.setDrawOverlapping(false);
        //tbv.setSelectionDelta(6);
        
        f.getContentPane().add(tbv, BorderLayout.CENTER);
	}
	
	public void setMinDate(String min){
        JaretDate date = new JaretDate();
        
        int month = Integer.parseInt(min.substring(0, 2));                     
        int day = Integer.parseInt(min.substring(3, 5));
        int hours = Integer.parseInt(min.substring(6, 8));
        int minutes = Integer.parseInt(min.substring(9, 11));
        int seconds = Integer.parseInt(min.substring(12, 14));
        date.setDateTime(day, month, 2013, hours, minutes, seconds); 
        
        System.out.println("min " + date.toDisplayString());
        
        tbv.setMinDate(date);
	}
	
	public void setMaxDate(String max){
		JaretDate date = new JaretDate();
        
        int month = Integer.parseInt(max.substring(0, 2));                     
        int day = Integer.parseInt(max.substring(3, 5));
        int hours = Integer.parseInt(max.substring(6, 8));
        int minutes = Integer.parseInt(max.substring(9, 11));
        int seconds = Integer.parseInt(max.substring(12, 14));
        date.setDateTime(day, month, 2013, hours, minutes, seconds); 
        
        tbv.setMaxDate(date);
        
        System.out.println("max " + date.toDisplayString());
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
            date.advanceMinutes(5);
                                    
            IntervalImpl interval = new IntervalImpl();
            interval.setBegin(date.copy());
            date.advanceSeconds(Double.parseDouble(duration));
            interval.setEnd(date.copy());
            tbr.addInterval(interval);
            model.addRow(tbr);
    }
}
