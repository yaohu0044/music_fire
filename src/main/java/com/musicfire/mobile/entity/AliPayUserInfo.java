package com.musicfire.mobile.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-11-22
 */
@TableName("ali_pay_user_info")
public class AliPayUserInfo{

    private static final long serialVersionUID = 1L;

    /**
     * 详细地址
     */
    private String address;
    /**
     * 区县名称
     */
    private String area;
    /**
     * 用户头像地址
     */
    private String avatar;
    /**
     * 经营业务范围
     */
    @TableField("business_scope")
    private String businessScope;
    /**
     * 证件号码
     */
    @TableField("cert_no")
    private String certNo;
    /**
     * 证件类型
     */
    @TableField("cert_type")
    private String certType;
    /**
     * 市名称
     */
    private String city;
    /**
     * 学校名称
     */
    @TableField("college_name")
    private String collegeName;
    /**
     * 国家码
     */
    @TableField("country_code")
    private String countryCode;
    /**
     * 学历
     */
    private String degree;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 入学时间
     */
    @TableField("enrollment_time")
    private String enrollmentTime;
    /**
     * 公司类型
     */
    @TableField("firm_type")
    private String firmType;
    /**
     * 性别
     */
    private String gender;
    /**
     * 预期毕业时间
     */
    @TableField("graduation_time")
    private String graduationTime;
    /**
     * 余额账户是否被冻结
     */
    @TableField("is_balance_frozen")
    private String isBalanceFrozen;
    /**
     * 是否通过实名认证
     */
    @TableField("is_certified")
    private String isCertified;
    /**
     * isStudentCertified
     */
    @TableField("is_student_certified")
    private String isStudentCertified;
    /**
     * 营业执照有效期
     */
    @TableField("license_expiry_date")
    private String licenseExpiryDate;
    /**
     * 企业执照号码
     */
    @TableField("license_no")
    private String licenseNo;
    /**
     * 支付宝会员等级
     */
    @TableField("member_grade")
    private String memberGrade;
    /**
     * 组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 个人用户生日
     */
    @TableField("person_birthday")
    private String personBirthday;
    /**
     * 证件有效期
     */
    @TableField("person_cert_expiry_date")
    private String personCertExpiryDate;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 职业
     */
    private String profession;
    /**
     * 省份
     */
    private String province;
    /**
     * 用户姓名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 淘宝id
     */
    @TableField("taobao_id")
    private String taobaoId;
    /**
     * 用户Id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 用户类型
     */
    @TableField("user_type")
    private String userType;
    /**
     * 用户状态
     */
    @TableField("user_status")
    private String userStatus;
    /**
     * 邮政编码
     */
    private String zip;
    /**
     * 登录次数
     */
    @TableField("login_times")
    private String loginTimes;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(String enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public String getFirmType() {
        return firmType;
    }

    public void setFirmType(String firmType) {
        this.firmType = firmType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(String graduationTime) {
        this.graduationTime = graduationTime;
    }

    public String getIsBalanceFrozen() {
        return isBalanceFrozen;
    }

    public void setIsBalanceFrozen(String isBalanceFrozen) {
        this.isBalanceFrozen = isBalanceFrozen;
    }

    public String getIsCertified() {
        return isCertified;
    }

    public void setIsCertified(String isCertified) {
        this.isCertified = isCertified;
    }

    public String getIsStudentCertified() {
        return isStudentCertified;
    }

    public void setIsStudentCertified(String isStudentCertified) {
        this.isStudentCertified = isStudentCertified;
    }

    public String getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(String licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getMemberGrade() {
        return memberGrade;
    }

    public void setMemberGrade(String memberGrade) {
        this.memberGrade = memberGrade;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getPersonBirthday() {
        return personBirthday;
    }

    public void setPersonBirthday(String personBirthday) {
        this.personBirthday = personBirthday;
    }

    public String getPersonCertExpiryDate() {
        return personCertExpiryDate;
    }

    public void setPersonCertExpiryDate(String personCertExpiryDate) {
        this.personCertExpiryDate = personCertExpiryDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTaobaoId() {
        return taobaoId;
    }

    public void setTaobaoId(String taobaoId) {
        this.taobaoId = taobaoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(String loginTimes) {
        this.loginTimes = loginTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        return "AliPayUserInfo{" +
        ", address=" + address +
        ", area=" + area +
        ", avatar=" + avatar +
        ", businessScope=" + businessScope +
        ", certNo=" + certNo +
        ", certType=" + certType +
        ", city=" + city +
        ", collegeName=" + collegeName +
        ", countryCode=" + countryCode +
        ", degree=" + degree +
        ", email=" + email +
        ", enrollmentTime=" + enrollmentTime +
        ", firmType=" + firmType +
        ", gender=" + gender +
        ", graduationTime=" + graduationTime +
        ", isBalanceFrozen=" + isBalanceFrozen +
        ", isCertified=" + isCertified +
        ", isStudentCertified=" + isStudentCertified +
        ", licenseExpiryDate=" + licenseExpiryDate +
        ", licenseNo=" + licenseNo +
        ", memberGrade=" + memberGrade +
        ", organizationCode=" + organizationCode +
        ", personBirthday=" + personBirthday +
        ", personCertExpiryDate=" + personCertExpiryDate +
        ", phone=" + phone +
        ", profession=" + profession +
        ", province=" + province +
        ", userName=" + userName +
        ", mobile=" + mobile +
        ", nickName=" + nickName +
        ", taobaoId=" + taobaoId +
        ", userId=" + userId +
        ", userType=" + userType +
        ", userStatus=" + userStatus +
        ", zip=" + zip +
        ", loginTimes=" + loginTimes +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
