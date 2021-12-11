package com.example.projectapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectapp.R
import com.example.projectapp.databinding.FragmentNotificationsBinding
import com.example.projectapp.databinding.FragmentProductsBinding
import com.example.projectapp.ui.activities.AddProductActivity
import com.example.projectapp.ui.view_models.NotificationsViewModel
import com.example.projectapp.ui.view_models.ProductsViewModel


class ProductsFragment : Fragment() {

    private lateinit var notificationsViewModel: ProductsViewModel
    private var _binding: FragmentProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) //Enabling the option menu
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(ProductsViewModel::class.java)

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    /**
     * Adds the 'Add Product' button to the fragment view.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Sets the action of the 'Add Product' button
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId //the id of the pressed item
        when(id){
            R.id.action_add_product ->{
                startActivity(Intent(activity, AddProductActivity::class.java ))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}