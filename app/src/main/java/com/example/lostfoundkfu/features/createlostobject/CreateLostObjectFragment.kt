package com.example.lostfoundkfu.features.createlostobject

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.data.Items.Building
import com.example.lostfoundkfu.data.Items.BuildingWithoutFlag
import com.example.lostfoundkfu.data.Items.LostItem
import com.example.lostfoundkfu.features.App
import com.example.lostfoundkfu.features.mainscreen.MainActivity
import com.example.lostfoundkfu.features.universallist.UseCases
import kotlinx.android.synthetic.main.add_item_fragment.*
import java.io.File
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CreateLostObjectFragment : MvpAppCompatFragment(), CreateLostObjectView,
    AdapterView.OnItemSelectedListener {

    var list_of_items = arrayOf("Эл. Техника", "Одежда", "Бижутерия", "Учебники", "Прочее")
    var objectType: String? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: CreateLostObjectPresenter

    @ProvidePresenter
    fun initPresenter() = presenter

    private val adapter = BuildingsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance
            .getAppComponent()
            .mainActivityComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        spinner.onItemSelectedListener = this
        spinner.adapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                list_of_items
            )
        }
        presenter.getBuildings(context!!)
        initToolbar()
        initClickListeners()
        initTextListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    val selectedImage =
                        data?.extras?.get("data") as Bitmap
                    iv_photo.setImageBitmap(selectedImage)
                }
            }
            200 -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    var selectedImageUri = data?.data
                    val path = selectedImageUri?.let { getPathFromURI(it) }
                    if (path != null) {
                        val f = File(path)
                        selectedImageUri = Uri.fromFile(f)
                    }
                    iv_photo.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun initTextListeners() {
        et_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                ti_title.error = null
            }
        })
    }

    private fun initClickListeners() {
        iv_photo.setOnClickListener {
            showDialog()
        }
        btn_accept.setOnClickListener {
            val name = et_name.text.toString()
            val description = et_title.text.toString()

            val calendar = GregorianCalendar(dp.year, dp.month, dp.dayOfMonth)
            val date = calendar.time

            if (description.isEmpty()) {
                ti_title.error = getString(R.string.error_title)
                return@setOnClickListener
            }
            if (name.isEmpty()){
                ti_name.error = getString(R.string.error_title)
                return@setOnClickListener
            }
            var bitmap: Bitmap?
            try {
                bitmap = (iv_photo.drawable as BitmapDrawable).bitmap
            } catch (e: Exception) {
                bitmap = null
            }

            if (bitmap != null) {
                val buildings = adapter.list
                val buildingsSelected = ArrayList<String>()
                for (i in buildings) {
                    if (i.flag)
                        i.name?.let { it1 -> buildingsSelected.add(it1) }
                }
                val isLost = arguments?.getBoolean(IS_LOST)
                if (isLost != null && isLost) {
                    presenter.addObject(name, description, buildingsSelected, date, objectType, bitmap, false)
                    (activity as MainActivity).openUniversalList(
                        UseCases.SupposedLostList,
                        LostItem(name, description, buildingsSelected, date, null, null,false, objectType),
                        null
                    )
                } else {
                    presenter.addObject(name, description, buildingsSelected, date, objectType, bitmap, true)
                    (activity as MainActivity).openUniversalList(
                        UseCases.SupposedFoundList,
                        LostItem(name, description, buildingsSelected, date, null, null,true, objectType),
                        null
                    )
                }
            } else {
                Toast.makeText(context, "Please, add photos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        objectType = list_of_items[position]
    }

    override fun showDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.choose_type))
                .setPositiveButton("Camera") { _, _ -> takePhoto() }
                .setNegativeButton(
                    "Gallery"
                ) { _, _ -> chooseFromDevise() }
                .show()
        }
    }

    override fun getBuildings(list: ArrayList<BuildingWithoutFlag>) {
        val buildingsWithFlag = ArrayList<Building>()
        for (building in list)
            buildingsWithFlag.add(Building(building.name, building.image, false))
        adapter.list = buildingsWithFlag
        adapter.notifyDataSetChanged()
    }

    fun takePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, TAKE_PICTURE)
    }

    fun chooseFromDevise() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(intent, getString(R.string.select_picture)),
            REQUEST_GET_SINGLE_FILE
        )
    }

    private fun initToolbar() {
        val activity = (activity as MainActivity)
        activity.setSupportActionBar(toolbar)
        val isLost = arguments?.getBoolean(IS_LOST)
        if (isLost != null && isLost)
            toolbar.title = "Создание потеряного элемента"
        else
            toolbar.title = "Создание находки"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

    fun getPathFromURI(contentUri: Uri): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context?.let { it.contentResolver.query(contentUri, proj, null, null, null) }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                res = cursor.getString(column_index)
            }
        }
        cursor?.close()
        return res
    }

    companion object {
        private const val IS_LOST = "isLost"
        private const val TAKE_PICTURE = 100
        private const val REQUEST_GET_SINGLE_FILE = 200
        fun newInstance(isLost: Boolean): CreateLostObjectFragment {
            val args = Bundle()
            args.putBoolean(IS_LOST, isLost)
            val fragment = CreateLostObjectFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
