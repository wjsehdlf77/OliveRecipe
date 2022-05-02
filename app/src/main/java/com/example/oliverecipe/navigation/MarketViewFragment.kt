package com.example.oliverecipe.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.oliverecipe.databinding.FragmentMarketBinding


class MarketViewFragment : Fragment() {

    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl("http://www.ssg.com/")
        }

        binding.urlEditText.setOnEditorActionListener { _, actionId, _ ->
            // 표현식(람다 함수는 마지막 줄이 리턴)
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var url = binding.urlEditText.text.toString()
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://$url"
                    binding.urlEditText.setText(url)
                }
                binding.webView.loadUrl(url)
                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}