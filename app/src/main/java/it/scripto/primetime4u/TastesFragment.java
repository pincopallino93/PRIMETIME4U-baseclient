package it.scripto.primetime4u;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dexafree.materialList.cards.model.Card;
import com.dexafree.materialList.controller.OnButtonPressListener;
import com.dexafree.materialList.view.MaterialListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import primetime4u.app.AppController;
import primetime4u.model.Movie;
import primetime4u.util.Utils;

public class TastesFragment extends BaseFragment {

    private MaterialListView tastes_material_list_view;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment TastesFragment.
     */
    public static TastesFragment newInstance() {
        return new TastesFragment();
    }

    public TastesFragment() {
        // Required empty public constructor
    }

    @Override
    protected String getTagLog() {
        return "TastesFragment";
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_tastes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        tastes_material_list_view = (MaterialListView) view.findViewById(R.id.tastes_material_list_view);

        String url = Utils.SERVER_API + "tastes/" + "pastorini.claudio@gmail.com" + "/movie";

        JsonObjectRequest proposalRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    List<Movie> tastesList = new ArrayList<>();

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        try {
                            JSONObject data = response.getJSONObject("data");
                            JSONArray tastes = data.getJSONArray("tastes");

                            for (int i = 0; i < tastes.length(); i++) {
                                JSONObject proposalJSON = tastes.getJSONObject(i);

                                Movie proposal = new Movie();
                                proposal.setOriginalTitle(proposalJSON.getString("original_title"));
                                proposal.setIdIMDB(proposalJSON.getString("id_IMDB"));
                                proposal.setPoster(proposalJSON.getString("poster"));

                                tastesList.add(proposal);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, e.toString());
                            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                        }

                        drawResult(tastesList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(proposalRequest);
        return view;
    }

    private void drawResult(List<Movie> proposalList) {
        for (int i = 0; i < proposalList.size(); i++) {
            Movie proposal = proposalList.get(i);

            String originalTitle = proposal.getOriginalTitle();

            final TasteCard movieCard = new TasteCard(context);
            movieCard.setTitle(originalTitle);
            movieCard.setTaste(true);
            movieCard.setDismissible(false);
            movieCard.setType(TasteCard.MOVIE_TYPE);
            movieCard.setDrawable(R.drawable.ic_launcher);
            movieCard.setOnTasteButtonPressedListener(new OnButtonPressListener() {
                @Override
                public void onButtonPressedListener(View view, Card card) {
                    String toastText = movieCard.getTaste() ? "Me gusta" : "Me disgusta";
                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
                }
            });
            
            tastes_material_list_view.add(movieCard);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle toSave) {
        super.onSaveInstanceState(toSave);

    }

}
