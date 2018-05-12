package ra.olympus.zeus.events;

import java.util.ArrayList;
import java.util.List;

public class DummyData {
    public static List<Event> getData(){
        List<Event> data = new ArrayList<>();
        List<Subscription> category = new ArrayList<>();

        Subscription education = new Subscription(R.drawable.education, "Education");
        Subscription religion = new Subscription(R.drawable.religion, "Religion");
        Subscription sports = new Subscription(R.drawable.sports, "Sports");
        Subscription entertainment = new Subscription(R.drawable.entertainment, "Entertainment");
        Subscription lifestyle = new Subscription(R.drawable.health_and_lifestyle, "Lifestyle");

//        Event kwadwo = new Event(R.drawable.background,"GESA WEEK", "12th March,2018");
//        Event richael = new Event(R.drawable.flyer1,"EPILOGO", "12th March,2018");
//        Event alfred = new Event(R.drawable.flyerr,"ALL WHITE PARTY", "12th March,2018");
//        Event selorm = new Event(R.drawable.flyer1,"EPILOGO", "12th March,2018");
//        Event fidel = new Event(R.drawable.flyerr,"ALL WHITE PARTY", "12th March,2018");
//        Event tom = new Event(R.drawable.background,"GESA WEEK", "12th March,2018");
//
//        data.add(kwadwo);
//        data.add(richael);
//        data.add(alfred);
//        data.add(selorm);
//        data.add(fidel);
//        data.add(tom);

        category.add(education);
        category.add(religion);
        category.add(sports);
        category.add(entertainment);
        category.add(lifestyle);

        return data;
    }

}
