package com.android.tools.Activities.Mvvm_ex.Model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class MainData {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("logo")
    @Expose
    private var logo: String? = null

    @SerializedName("d_title")
    @Expose
    private var dTitle: String? = null

    @SerializedName("one_key")
    @Expose
    private var oneKey: String? = null

    @SerializedName("one_hash")
    @Expose
    private var oneHash: String? = null

    @SerializedName("r_key")
    @Expose
    private var rKey: String? = null

    @SerializedName("r_hash")
    @Expose
    private var rHash: String? = null

    @SerializedName("currency")
    @Expose
    private var currency: String? = null

    @SerializedName("timezone")
    @Expose
    private var timezone: String? = null

    @SerializedName("policy")
    @Expose
    private var policy: String? = null

    @SerializedName("about")
    @Expose
    private var about: String? = null

    @SerializedName("contact")
    @Expose
    private var contact: String? = null

    @SerializedName("terms")
    @Expose
    private var terms: String? = null

    @SerializedName("p_limit")
    @Expose
    private var pLimit: String? = null

    @SerializedName("pdbanner")
    @Expose
    private var pdbanner: String? = null

    @SerializedName("signupcredit")
    @Expose
    private var signupcredit: String? = null

    @SerializedName("refercredit")
    @Expose
    private var refercredit: String? = null

    @SerializedName("asid")
    @Expose
    private var asid: String? = null

    @SerializedName("token")
    @Expose
    private var token: String? = null

    @SerializedName("megic_Num")
    @Expose
    private var megicNum: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getLogo(): String? {
        return logo
    }

    fun setLogo(logo: String?) {
        this.logo = logo
    }

    fun getdTitle(): String? {
        return dTitle
    }

    fun setdTitle(dTitle: String?) {
        this.dTitle = dTitle
    }

    fun getOneKey(): String? {
        return oneKey
    }

    fun setOneKey(oneKey: String?) {
        this.oneKey = oneKey
    }

    fun getOneHash(): String? {
        return oneHash
    }

    fun setOneHash(oneHash: String?) {
        this.oneHash = oneHash
    }

    fun getrKey(): String? {
        return rKey
    }

    fun setrKey(rKey: String?) {
        this.rKey = rKey
    }

    fun getrHash(): String? {
        return rHash
    }

    fun setrHash(rHash: String?) {
        this.rHash = rHash
    }

    fun getCurrency(): String? {
        return currency
    }

    fun setCurrency(currency: String?) {
        this.currency = currency
    }

    fun getTimezone(): String? {
        return timezone
    }

    fun setTimezone(timezone: String?) {
        this.timezone = timezone
    }

    fun getPolicy(): String? {
        return policy
    }

    fun setPolicy(policy: String?) {
        this.policy = policy
    }

    fun getAbout(): String? {
        return about
    }

    fun setAbout(about: String?) {
        this.about = about
    }

    fun getContact(): String? {
        return contact
    }

    fun setContact(contact: String?) {
        this.contact = contact
    }

    fun getTerms(): String? {
        return terms
    }

    fun setTerms(terms: String?) {
        this.terms = terms
    }

    fun getpLimit(): String? {
        return pLimit
    }

    fun setpLimit(pLimit: String?) {
        this.pLimit = pLimit
    }

    fun getPdbanner(): String? {
        return pdbanner
    }

    fun setPdbanner(pdbanner: String?) {
        this.pdbanner = pdbanner
    }

    fun getSignupcredit(): String? {
        return signupcredit
    }

    fun setSignupcredit(signupcredit: String?) {
        this.signupcredit = signupcredit
    }

    fun getRefercredit(): String? {
        return refercredit
    }

    fun setRefercredit(refercredit: String?) {
        this.refercredit = refercredit
    }

    fun getAsid(): String? {
        return asid
    }

    fun setAsid(asid: String?) {
        this.asid = asid
    }

    fun getToken(): String? {
        return token
    }

    fun setToken(token: String?) {
        this.token = token
    }

    fun getMegicNum(): String? {
        return megicNum
    }

    fun setMegicNum(megicNum: String?) {
        this.megicNum = megicNum
    }
}