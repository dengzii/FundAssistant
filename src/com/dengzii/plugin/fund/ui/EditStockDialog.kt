package com.dengzii.plugin.fund.ui

import com.dengzii.plugin.fund.api.bean.StockUpdateBean
import com.dengzii.plugin.fund.design.EditStockForm
import com.dengzii.plugin.fund.model.UserStockModel
import com.dengzii.plugin.fund.tools.ui.ColumnInfo
import com.dengzii.plugin.fund.tools.ui.TableAdapter
import com.dengzii.plugin.fund.tools.ui.onClick

class EditStockDialog(
    val origin: MutableMap<String, UserStockModel>,
    val callback: (Map<String, UserStockModel>) -> Unit
) : EditStockForm("编辑股票列表") {

    private val tableData = mutableListOf<MutableList<Any?>>()
    private val columnInfos = mutableListOf<ColumnInfo<Any>>()
    private val tableAdapter = TableAdapter(tableData, columnInfos)

    companion object {

        fun show(s: Map<String, UserStockModel>, callback: (Map<String, UserStockModel>) -> Unit) {
            val dialog = EditStockDialog(s.toMutableMap(), callback)
            dialog.packAndShow()
        }
    }

    override fun onOpened() {
        super.onOpened()
        columnInfos.add(ColumnInfo.new("代码", false))
        columnInfos.add(ColumnInfo.new("名称", false))
        columnInfos.add(ColumnInfo.new("成本价", false))
        columnInfos.add(ColumnInfo.new("持有数量", false))
        tableAdapter.setup(table)
        tableAdapter.fireTableStructureChanged()

        textFieldSearch.text = origin.keys.joinToString { it }
        buttonCancel.onClick {
            hideAndDispose()
        }
        buttonOk.onClick {
            val s = textFieldSearch.text.split(",").filter { it.isNotEmpty() }
            s.forEach {
                val code = it.trim()
                if (!origin.containsKey(code)) {
                    origin[code] = UserStockModel(StockUpdateBean())
                }
            }
            callback.invoke(origin)
            hideAndDispose()
        }
    }
}