package com.example.thegoodchef.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thegoodchef.databinding.AcercaBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AcercaFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "AboutBottomDialog"
    }

    private var _binding: AcercaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AcercaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val manager = context?.packageManager
            val info = manager?.getPackageInfo(
                requireContext().packageName, 0
            )

            val versionName = info?.versionName
            val versionNumber = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                info?.longVersionCode
            } else {
                info?.versionCode
            }

            val appVersionTxt = "$versionName ($versionNumber)"
            appVersion.text = appVersionTxt

            repoLink.setOnClickListener {
                val url: Uri = Uri.parse("https://github.com/michaelmartinezw/thegoodchef.git")
                val intent = Intent(Intent.ACTION_VIEW, url)
                if (intent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(intent)
                }
            }

            linkedInLink.setOnClickListener {
                val recipeUrl: Uri = Uri.parse("https://www.linkedin.com/in/michael-williams-martinez-3b70ab7a")
                val intent = Intent(Intent.ACTION_VIEW, recipeUrl)
                if (intent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(intent)
                }
            }

            githubLink.setOnClickListener {
                val recipeUrl: Uri = Uri.parse("https://github.com/michaelmartinezw")
                val intent = Intent(Intent.ACTION_VIEW, recipeUrl)
                if (intent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(intent)
                }
            }

            mailLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                val data = Uri.parse(
                    "mailto:mmaik1987@gmail.com?subject=" + Uri.encode("Regarding Appita:")
                )
                intent.data = data
                startActivity(intent)
            }
        }
    }

    fun newInstance(): AcercaFragment {
        return AcercaFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}