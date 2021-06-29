package com.example.newsreaderfactory.MVP.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.newsreaderfactory.MVP.model.Article
import com.example.newsreaderfactory.MVP.presenter.ArticlePresenter
import com.example.newsreaderfactory.R
import com.example.newsreaderfactory.adapter.DetailArticleAdapter
import com.example.newsreaderfactory.adapter.ListArticleAdapter
import kotlinx.android.synthetic.main.fragment_detail_article.*
import kotlinx.android.synthetic.main.fragment_list_article.*

class DetailArticleFragment : Fragment(R.layout.fragment_detail_article), ArticleInterface {

    private val args: DetailArticleFragmentArgs by navArgs()
    lateinit var presenter: ArticlePresenter
    var prog: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_detail_article, container, false)
        prog = view.findViewById(R.id.prgoress_bar_pv)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = ArticlePresenter(this, requireContext())
        presenter.getData()
    }

    override fun showLoading() {
        prog?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        prog?.visibility = View.GONE
    }

    override fun showError(msg: String) {
        val ad: AlertDialog = AlertDialog.Builder(context).create()
        ad.setCancelable(false)
        ad.setTitle("Greška!")
        ad.setMessage(msg)
        ad.setButton("Ok",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        ad.show()
    }

    override fun showResult(result: Any) {
        detail_view_pager.visibility = View.VISIBLE
        val data = result as ArrayList<Article>
        val adapter = DetailArticleAdapter(data)
        detail_view_pager.adapter = adapter
        detail_view_pager.offscreenPageLimit = 10
        detail_view_pager.setCurrentItem(args.id-1, true)
    }

}