package ch.beerpro.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.beerpro.R;
import ch.beerpro.dummy.DummyContent;
import ch.beerpro.dummy.DummyContent.Beer;
import ch.beerpro.viewmodels.BeersViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchResultFragment extends Fragment {

    private static final String TAG = "SearchResultFragment";

    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private View emptyView;
    private CurrentSearchTermViewModel model;
    private SearchResultRecyclerViewAdapter adapter;
    private MyAdapterDataObserver adapterDataObserver;

    public SearchResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchresult_list, container, false);

        Log.i(TAG, "onCreateView");

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.emptyView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach");

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
            adapter = new SearchResultRecyclerViewAdapter(mListener);
            //adapterDataObserver = new MyAdapterDataObserver(adapter);
            //adapter.registerAdapterDataObserver(adapterDataObserver);

            model = ViewModelProviders.of(getActivity()).get(CurrentSearchTermViewModel.class);
            model.getCurrentSearchTerm().observe(getActivity(), adapter.getFilter()::filter);

            ViewModelProviders.of(getActivity()).get(BeersViewModel.class).getBeers()
                    .observe(getActivity(), adapter::setListItems);

        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");

        mListener = null;
        //adapter.unregisterAdapterDataObserver(adapterDataObserver);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Beer item);
    }

    private class MyAdapterDataObserver extends RecyclerView.AdapterDataObserver {

        private final RecyclerView.Adapter adapter;

        public MyAdapterDataObserver(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onChanged() {
            super.onChanged();
            checkEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkEmpty();
        }

        void checkEmpty() {
            Log.i(TAG, "checkEmpty? " + (adapter.getItemCount() == 0));

            if (adapter.getItemCount() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}
