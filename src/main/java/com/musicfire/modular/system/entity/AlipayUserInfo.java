package com.musicfire.modular.system.entity;

import lombok.ToString;

import java.util.Date;


@ToString
public class AlipayUserInfo {
    /**
     * 用户Id
     */
    private String userId;

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

    private String businessScope;

    /**
     * 证件号码
     */

    private String certNo;

    /**
     * 证件类型
     */

    private String certType;

    /**
     * 市名称
     */
    private String city;

    /**
     * 学校名称
     */

    private String collegeName;

    /**
     * 国家码
     */
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

    private String enrollmentTime;

    /**
     * 公司类型
     */

    private String firmType;

    /**
     * 性别
     */
    private String gender;

    /**
     * 预期毕业时间
     */

    private String graduationTime;

    /**
     * 余额账户是否被冻结
     */

    private String isBalanceFrozen;

    /**
     * 是否通过实名认证
     */

    private String isCertified;

    /**
     * isStudentCertified
     */

    private String isStudentCertified;

    /**
     * 营业执照有效期
     */

    private String licenseExpiryDate;

    /**
     * 企业执照号码
     */

    private String licenseNo;

    /**
     * 支付宝会员等级
     */

    private String memberGrade;

    /**
     * 组织机构代码
     */

    private String organizationCode;

    /**
     * 个人用户生日
     */

    private String personBirthday;

    /**
     * 证件有效期
     */

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

    private String userName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户昵称
     */

    private String nickName;

    /**
     * 淘宝id
     */

    private String taobaoId;

    /**
     * 用户类型
     */

    private String userType;

    /**
     * 用户状态
     */

    private String userStatus;

    /**
     * 邮政编码
     */
    private String zip;

    /**
     * 登录次数
     */

    private String loginTimes;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 修改时间
     */

    private Date updateTime;

    /**
     * 获取用户Id
     *
     * @return user_id - 用户Id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户Id
     *
     * @param userId 用户Id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取区县名称
     *
     * @return area - 区县名称
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置区县名称
     *
     * @param area 区县名称
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取用户头像地址
     *
     * @return avatar - 用户头像地址
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置用户头像地址
     *
     * @param avatar 用户头像地址
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取经营业务范围
     *
     * @return business_scope - 经营业务范围
     */
    public String getBusinessScope() {
        return businessScope;
    }

    /**
     * 设置经营业务范围
     *
     * @param businessScope 经营业务范围
     */
    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    /**
     * 获取证件号码
     *
     * @return cert_no - 证件号码
     */
    public String getCertNo() {
        return certNo;
    }

    /**
     * 设置证件号码
     *
     * @param certNo 证件号码
     */
    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    /**
     * 获取证件类型
     *
     * @return cert_type - 证件类型
     */
    public String getCertType() {
        return certType;
    }

    /**
     * 设置证件类型
     *
     * @param certType 证件类型
     */
    public void setCertType(String certType) {
        this.certType = certType;
    }

    /**
     * 获取市名称
     *
     * @return city - 市名称
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市名称
     *
     * @param city 市名称
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取学校名称
     *
     * @return college_name - 学校名称
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * 设置学校名称
     *
     * @param collegeName 学校名称
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * 获取国家码
     *
     * @return country_code - 国家码
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 设置国家码
     *
     * @param countryCode 国家码
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 获取学历
     *
     * @return degree - 学历
     */
    public String getDegree() {
        return degree;
    }

    /**
     * 设置学历
     *
     * @param degree 学历
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取入学时间
     *
     * @return enrollment_time - 入学时间
     */
    public String getEnrollmentTime() {
        return enrollmentTime;
    }

    /**
     * 设置入学时间
     *
     * @param enrollmentTime 入学时间
     */
    public void setEnrollmentTime(String enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    /**
     * 获取公司类型
     *
     * @return firm_type - 公司类型
     */
    public String getFirmType() {
        return firmType;
    }

    /**
     * 设置公司类型
     *
     * @param firmType 公司类型
     */
    public void setFirmType(String firmType) {
        this.firmType = firmType;
    }

    /**
     * 获取性别
     *
     * @return gender - 性别
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 获取预期毕业时间
     *
     * @return graduation_time - 预期毕业时间
     */
    public String getGraduationTime() {
        return graduationTime;
    }

    /**
     * 设置预期毕业时间
     *
     * @param graduationTime 预期毕业时间
     */
    public void setGraduationTime(String graduationTime) {
        this.graduationTime = graduationTime;
    }

    /**
     * 获取余额账户是否被冻结
     *
     * @return is_balance_frozen - 余额账户是否被冻结
     */
    public String getIsBalanceFrozen() {
        return isBalanceFrozen;
    }

    /**
     * 设置余额账户是否被冻结
     *
     * @param isBalanceFrozen 余额账户是否被冻结
     */
    public void setIsBalanceFrozen(String isBalanceFrozen) {
        this.isBalanceFrozen = isBalanceFrozen;
    }

    /**
     * 获取是否通过实名认证
     *
     * @return is_certified - 是否通过实名认证
     */
    public String getIsCertified() {
        return isCertified;
    }

    /**
     * 设置是否通过实名认证
     *
     * @param isCertified 是否通过实名认证
     */
    public void setIsCertified(String isCertified) {
        this.isCertified = isCertified;
    }

    /**
     * 获取isStudentCertified
     *
     * @return is_student_certified - isStudentCertified
     */
    public String getIsStudentCertified() {
        return isStudentCertified;
    }

    /**
     * 设置isStudentCertified
     *
     * @param isStudentCertified isStudentCertified
     */
    public void setIsStudentCertified(String isStudentCertified) {
        this.isStudentCertified = isStudentCertified;
    }

    /**
     * 获取营业执照有效期
     *
     * @return license_expiry_date - 营业执照有效期
     */
    public String getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    /**
     * 设置营业执照有效期
     *
     * @param licenseExpiryDate 营业执照有效期
     */
    public void setLicenseExpiryDate(String licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    /**
     * 获取企业执照号码
     *
     * @return license_no - 企业执照号码
     */
    public String getLicenseNo() {
        return licenseNo;
    }

    /**
     * 设置企业执照号码
     *
     * @param licenseNo 企业执照号码
     */
    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    /**
     * 获取支付宝会员等级
     *
     * @return member_grade - 支付宝会员等级
     */
    public String getMemberGrade() {
        return memberGrade;
    }

    /**
     * 设置支付宝会员等级
     *
     * @param memberGrade 支付宝会员等级
     */
    public void setMemberGrade(String memberGrade) {
        this.memberGrade = memberGrade;
    }

    /**
     * 获取组织机构代码
     *
     * @return organization_code - 组织机构代码
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * 设置组织机构代码
     *
     * @param organizationCode 组织机构代码
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    /**
     * 获取个人用户生日
     *
     * @return person_birthday - 个人用户生日
     */
    public String getPersonBirthday() {
        return personBirthday;
    }

    /**
     * 设置个人用户生日
     *
     * @param personBirthday 个人用户生日
     */
    public void setPersonBirthday(String personBirthday) {
        this.personBirthday = personBirthday;
    }

    /**
     * 获取证件有效期
     *
     * @return person_cert_expiry_date - 证件有效期
     */
    public String getPersonCertExpiryDate() {
        return personCertExpiryDate;
    }

    /**
     * 设置证件有效期
     *
     * @param personCertExpiryDate 证件有效期
     */
    public void setPersonCertExpiryDate(String personCertExpiryDate) {
        this.personCertExpiryDate = personCertExpiryDate;
    }

    /**
     * 获取电话号码
     *
     * @return phone - 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话号码
     *
     * @param phone 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取职业
     *
     * @return profession - 职业
     */
    public String getProfession() {
        return profession;
    }

    /**
     * 设置职业
     *
     * @param profession 职业
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * 获取省份
     *
     * @return province - 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取用户姓名
     *
     * @return user_name - 用户姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户姓名
     *
     * @param userName 用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取用户昵称
     *
     * @return nick_name - 用户昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置用户昵称
     *
     * @param nickName 用户昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取淘宝id
     *
     * @return taobao_id - 淘宝id
     */
    public String getTaobaoId() {
        return taobaoId;
    }

    /**
     * 设置淘宝id
     *
     * @param taobaoId 淘宝id
     */
    public void setTaobaoId(String taobaoId) {
        this.taobaoId = taobaoId;
    }

    /**
     * 获取用户类型
     *
     * @return user_type - 用户类型
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 设置用户类型
     *
     * @param userType 用户类型
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 获取用户状态
     *
     * @return user_status - 用户状态
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * 设置用户状态
     *
     * @param userStatus 用户状态
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 获取邮政编码
     *
     * @return zip - 邮政编码
     */
    public String getZip() {
        return zip;
    }

    /**
     * 设置邮政编码
     *
     * @param zip 邮政编码
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 获取登录次数
     *
     * @return login_times - 登录次数
     */
    public String getLoginTimes() {
        return loginTimes;
    }

    /**
     * 设置登录次数
     *
     * @param loginTimes 登录次数
     */
    public void setLoginTimes(String loginTimes) {
        this.loginTimes = loginTimes;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}