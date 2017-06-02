package org.example.moex.ui.shares

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by 5turman on 23.03.2017.
 */
class SearchViewState(val isIconified: Boolean, val query: String) : Parcelable {

    constructor(source: Parcel) : this(
            source.readInt() != 0,
            source.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(if (isIconified) 1 else 0)
        dest.writeString(query)
    }

    override fun describeContents() = 0

    companion object {

        @JvmField
        val CREATOR = object : Parcelable.Creator<SearchViewState> {

            override fun createFromParcel(source: Parcel) = SearchViewState(source)

            override fun newArray(size: Int): Array<SearchViewState> = arrayOf()
        }

    }
}
