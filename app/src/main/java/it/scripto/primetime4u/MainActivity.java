package it.scripto.primetime4u;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import it.scripto.primetime4u.utils.BaseActivity;

public class MainActivity extends BaseActivity implements WatchedFragment.onTasteChangeListener {

    private String account;
    private MainAdapter adapter;

    public boolean tasteTab;
    public boolean inflate;
    private String IMDB_SEARCH_LINK = "http://www.imdb.com/xml/find?json=1&q=";

    @Override
    protected String getTagLog() {
        return "MainActivity";
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(TutorialActivity.PREFERENCES, Context.MODE_PRIVATE);

        account = preferences.getString(TutorialActivity.ACCOUNT, null);

        // Get and set toolbar as action bar
        Toolbar main_activity_toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(main_activity_toolbar);
 
        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        adapter = new MainAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.main_activity_pager_sliding_tab_strip);
        tabs.setViewPager(pager);
    }

    public void refreshTastes(){
       adapter.notifyDataSetChanged();
    }

    public String getAccount() {
        return account;
    }
    
    @Override
    public void onTasteChanged() {
        refreshTastes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!tasteTab) getMenuInflater().inflate(R.menu.menu_main_nosearch, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //questo metodo viene chiamato per cambiare dinamicamente il menu


        if (tasteTab){
            if (inflate) {
                getMenuInflater().inflate(R.menu.menu_main, menu);
                inflate = false;
            }
            MenuItem searchItem = menu.findItem(R.id.searchButton);
            if (searchItem!=null){
                final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
                searchView.setQueryHint("Movie/artist, es: Matrix, Di Caprio"); //suggerimento ricerca

                searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if (s==null || s.isEmpty() || s.length()==0){
                            Toast.makeText(getBaseContext(), "Non hai cercato nulla", Toast.LENGTH_LONG).show();
                        }
                        String rebuilt = s.replace(" ","+"); //sostituisco spazi con +
                        String url = IMDB_SEARCH_LINK + rebuilt;

                        addTaste(url);

                        //Toast.makeText(getBaseContext(),url,Toast.LENGTH_LONG).show();

                        System.out.println("cerco su "+url);
                        searchView.clearFocus();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });


            }

        }
        else inflate = true;
        return true;
    }
    private void addTaste(String url){
//
//
//        //metodo che legge da imdb il json dell'url e aggiunge l'attore/film cercato ai gusti
//        /**
//         * Formato risposta da IMDB, se artista:
//         * {
//         *   "name_popular" : [ { "id":nm010101, ... } ]
//         *   "name_approx" : [ { .... } ]
//         * }
//         *
//         * se film:
//         *
//         * {
//         *    "title_popular" : [ { "id":tt9191919, .. } ]
//         *
//         * }
//         *
//         */
//        final String[] id = new String[1];
//        final String[] type = new String[1];
//
//        JsonObjectRequest imdbReq = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        //tenere conto caso film/artista
//                        if (response.has("title_popular")){
//                            //film
//                            try {
//                                Toast.makeText(getBaseContext(),"Il film verrà aggiunto alla tua lista gusti, attendi...",Toast.LENGTH_LONG).show();
//                                JSONArray popArray= response.getJSONArray("title_popular");
//                                JSONObject movie = popArray.getJSONObject(0);
//                                id[0] = movie.getString("id");
//                                type[0] = "movie";
//
//
//                            }
//                            catch (JSONException e){
//                                Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//                                return;
//                            }
//
//                        }
//                        else if (response.has("name_popular")){
//                            //artista
//                            try {
//                                Toast.makeText(getBaseContext(),"L'artista verrà aggiunto alla tua lista gusti, attendi...",Toast.LENGTH_LONG).show();
//                                JSONArray popArray= response.getJSONArray("name_popular");
//                                JSONObject movie = popArray.getJSONObject(0);
//                                id[0] = movie.getString("id");
//                                type[0] = "artist";
//
//
//
//                            }
//                            catch (JSONException e){
//                                Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//                                return;
//                            }
//                        }
//                        else {
//                            Toast.makeText(getBaseContext(),"Provare con una ricerca più specifica",Toast.LENGTH_LONG).show();
//                            return;
//                        }
//
//                        addToServer(id[0],type[0]);
//                    }
//                },
//        );
    }

    private void addToServer(String idimdb,String type){
//        /**
//         * POST su nostro server:
//         * ATTENZIONE, FA IL POST DUE VOLTE DI SEGUITO
//         * /api/tastes/<user_id>/<type>  POST dove type: artist , movie o genre
//         * {
//         *     "idIMDB" = id
//         * }
//         */
//
//        String url = Utils.SERVER_API + "tastes/" + account + "/" + type;
//        JsonObject toBePosted = new JsonObject();
//
//        toBePosted.addProperty("idIMDB",idimdb);
//
//
//        Ion.with(getBaseContext())
//                .load("POST",url)
//                .setJsonObjectBody(toBePosted)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        refreshTastes();
//                    }
//                });

    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //gestione del tasto impostazioni, modificare impostazioni su notifiche
        }
        if (id == R.id.searchButton){

            //gestione in onPrepareOptionMenu

        }

        return super.onOptionsItemSelected(item);
    }

    private class MainAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {
                getResources().getString(R.string.proposal_tab),
                getResources().getString(R.string.tastes_tab),
                getResources().getString(R.string.watched_tab)};

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ProposalFragment.newInstance();
                case 1:
                    return TastesFragment.newInstance();
                case 2:
                    return WatchedFragment.newInstance();
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }
    }
}
