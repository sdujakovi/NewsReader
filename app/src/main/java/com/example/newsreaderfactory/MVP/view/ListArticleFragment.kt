package com.example.newsreaderfactory.MVP.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsreaderfactory.MVP.model.Article
import com.example.newsreaderfactory.MVP.presenter.ArticlePresenter
import com.example.newsreaderfactory.R
import com.example.newsreaderfactory.adapter.ListArticleAdapter
import com.example.newsreaderfactory.database.MyDatabase
import com.example.newsreaderfactory.database.UserPreferences
import kotlinx.android.synthetic.main.fragment_list_article.*
import kotlinx.android.synthetic.main.loading.*
import kotlinx.coroutines.launch


class ListArticleFragment : Fragment(R.layout.fragment_list_article), ArticleInterface {

    lateinit var presenter: ArticlePresenter
    var prog: ProgressBar? = null
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userPreferences = UserPreferences(requireContext())
        presenter = ArticlePresenter(this)
        presenter.getData()


        val view: View = inflater.inflate(R.layout.fragment_list_article, container, false)
        prog = view.findViewById(R.id.prgoress_bar)

        return view
    }



    override fun showLoading() {
        prog?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        prog?.visibility = View.GONE
    }

    override fun showError(msg: String) {
        val ad: AlertDialog = AlertDialog.Builder(context)
            .create()
        ad.setCancelable(false)
        ad.setTitle("Greška!")
        ad.setMessage(msg)
        ad.setButton("Ok",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        ad.show()
    }

    override fun showResult(result: Any) {
        article_recycler_view.visibility = View.VISIBLE

        val data = result as ArrayList<Article>
        val adapter = ListArticleAdapter(data, articleItemListener)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        article_recycler_view.adapter = adapter
        article_recycler_view.layoutManager = layoutManager
        (article_recycler_view.adapter as ListArticleAdapter).notifyDataSetChanged()
    }

    private val articleItemListener = ListArticleAdapter.OnClickListener{ article ->
        val action = ListArticleFragmentDirections.actionListArticleFragmentToDetailArticleFragment()
        findNavController().navigate(action)
    }
}