package com.example.photofilters.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photofilters.data.models.PhotoFiltersModel
import com.example.photofilters.databinding.PhotofiltersFragmentBinding
import com.example.photofilters.ui.adapters.PhotoFiltersAdapter
import com.example.photofilters.utils.saveImage
import com.example.photofilters.viewmodels.PhotoFiltersViewModel
import kotlinx.coroutines.launch

class PhotoFiltersFragment : Fragment() {

    private val vm: PhotoFiltersViewModel by viewModels()

    private var _binding: PhotofiltersFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager : LinearLayoutManager
    private var adapter : PhotoFiltersAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhotofiltersFragmentBinding.inflate(inflater, container, false)
        updateUi()
        observeLiveData()
        clickButtonListener()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi() {
        layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.overlayView.layoutManager = layoutManager
    }

    private fun observeLiveData() {
        vm.apply {

            repository.photoFiltersData.observe(viewLifecycleOwner) { photofilters ->
                photofilters.let {
                    binding.fetchProgress.visibility = View.VISIBLE
                    binding.overlayView.visibility = View.VISIBLE
                    val arrayList = ArrayList<PhotoFiltersModel>(it)
                    arrayList.add(0, PhotoFiltersModel(0, "None",
                        "https://i.hizliresim.com/h93bjpf.png",
                        "https://i.hizliresim.com/h93bjpf.png", null))
                    adapter = PhotoFiltersAdapter(arrayList, clickListener())
                    if(binding.overlayView.adapter == null) {
                        binding.overlayView.adapter = adapter
                    }
                    binding.fetchProgress.visibility = View.GONE
                    lifecycleScope.launch {
                        vm.updateBitmapDb(context!!, arrayList)
                    }
                }
            }

            selectedBitmap.observe(viewLifecycleOwner) { photoFiltersDb ->
                photoFiltersDb.overlayImageBitMap?.let {
                    binding.customView.drawEffect(photoFiltersDb.overlayId,
                        it
                    )
                }
            }

            repository.isInProgress.observe(viewLifecycleOwner) { isLoading ->
                isLoading.let {
                    if (it) {
                        binding.overlayView.visibility = View.GONE
                        binding.fetchProgress.visibility = View.VISIBLE
                    } else {
                        binding.fetchProgress.visibility = View.GONE
                    }
                }
            }

            repository.isError.observe(viewLifecycleOwner) { isError ->
                isError.let {
                    binding.fetchProgress.visibility =  if (it) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }
    }

    private fun clickButtonListener() {
        binding.apply {
            saveButton.setOnClickListener {
                saveImage(context!!, customView)
            }
        }
    }

    private fun clickListener() : PhotoFiltersAdapter.OnClickListener {
        return PhotoFiltersAdapter.OnClickListener { photoFiltersItem ->
            binding.customView.apply {
                if (photoFiltersItem.overlayImageBitMap != null) {
                    drawEffect(photoFiltersItem.overlayId, photoFiltersItem.overlayImageBitMap!! )
                } else {
                    lifecycleScope.launch {
                        drawEffect(photoFiltersItem.overlayId, vm.getBitmap(context!!, photoFiltersItem.overlayUrl))
                    }
                }
            }
        }
    }

}