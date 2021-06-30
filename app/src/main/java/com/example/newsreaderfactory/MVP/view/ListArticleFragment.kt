package com.example.newsreaderfactory.MVP.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsreaderfactory.MVP.model.Article
import com.example.newsreaderfactory.MVP.presenter.ArticlePresenter
import com.example.newsreaderfactory.R
import com.example.newsreaderfactory.adapter.ListArticleAdapter
import kotlinx.android.synthetic.main.fragment_list_article.*


class ListArticleFragment : Fragment(R.layout.fragment_list_article), ArticleInterface {

    lateinit var presenter: ArticlePresenter
    var progress_bar: ProgressBar? = null
    private val articleItemListener = ListArticleAdapter.OnClickListener{
        val action = ListArticleFragmentDirections.actionListArticleFragmentToDetailArticleFragment(
            it.id
        )
        findNavController().navigate(action)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list_article, container, false)
        progress_bar = view.findViewById(R.id.prgoress_bar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = ArticlePresenter(this, requireContext())
        presenter.getData()
    }

    override fun showLoading() {
        progress_bar?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar?.visibility = View.GONE
    }

    override fun showError(msg: String) {
        val ad: AlertDialog = AlertDialog.Builder(context).create()
            ad.setCancelable(false)
            ad.setTitle("Greška!")
            ad.setMessage(msg)
            ad.setButton("Ok", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            ad.show()
    }

    override fun showResult(result: Any) {
        val data = result as ArrayList<Article>
        val adapter = ListArticleAdapter(data, articleItemListener)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        article_recycler_view.adapter = adapter
        article_recycler_view.layoutManager = layoutManager
        (article_recycler_view.adapter as ListArticleAdapter).notifyDataSetChanged()
    }


}