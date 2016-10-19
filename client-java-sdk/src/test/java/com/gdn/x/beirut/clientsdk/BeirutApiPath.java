package com.gdn.x.beirut.clientsdk;

public class BeirutApiPath {
  public static final String APPLY_NEW_POSITION = "/candidate/applyNewPosition";

  public static final String DELETE_CANDIDATE = "/candidate/deleteCandidate";
  public static final String DELETE_POSITION = "/position/deletePosition";
  public static final String FIND_CANDIDATE_BY_CREATED_DATE_BETWEEN_AND_STOREID =
      "/candidate/findCandidateByCreatedDateBetweenAndStoreId";
  public static final String FIND_CANDIDATE_BY_EMAIL_ADDRESS_AND_STOREID =
      "/candidate/findCandidateByEmailAddressAndStoreId";
  public static final String FIND_CANDIDATE_BY_FIRST_NAME_CONTAIN_AND_STOREID =
      "/candidate/findCandidateByFirstNameContainAndStoreId";
  public static final String FIND_CANDIDATE_BY_STOREID_EAGER =
      "/candidate/findCandidateByIdAndStoreIdEager";
  public static final String FIND_CANDIDATE_BY_STOREID_LAZY =
      "/candidate/findCandidateByIdAndStoreIdLazy";
  public static final String FIND_CANDIDATE_BY_LAST_NAME_CONTAIN_AND_STOREID =
      "/candidate/findCandidateByLastNameContainAndStoreId";
  public static final String FIND_CANDIDATE_BY_PHONE_NUMBER_CONTAIN_AND_STOREID =
      "/candidate/findCandidateByPhoneNumberContainAndStoreId";
  public static final String FIND_CANDIDATE_DETAIL_AND_STOREID =
      "/candidate/findCandidateDetailAndStoreId";
  public static final String GET_ALL_CANDIDATE_BY_STOREID_AND_MARK_FOR_DELETE_WITH_PAGEABLE =
      "/candidate/getAllCandidatesByStoreIdAndMarkForDeleteWithPageable";
  public static final String GET_ALL_CANDIDATE_BY_STORE_ID_WITH_PAGEABLE =
      "/candidate/getAllCandidatesByStoreIdWithPageable";
  public static final String GET_ALL_POSITION_BY_STOREID = "/position/getAllPosition";
  public static final String GET_ALL_POSITION_WITH_PAGEABLE =
      "/position/getAllPositionWithPageable";
  public static final String GET_CANDIDATE_POSITION_BY_SOLR_QUERY =
      "/candidate/getCandidatePositionBySolrQuery";
  public static final String GET_CANDIDATE_POSITION_DETAIL_BY_STOREID_WITH_LOGS =
      "/candidate/getCandidatePositionDetailByStoreIdWithLogs";
  public static final String GET_POSITION_BY_STOREID_AND_MARK_FOR_DELETE =
      "/position/getPositionByStoreIdAndMarkForDelete";
  public static final String GET_POSITION_TITLE = "/position/getPositionByTitle";
  public static final String GET_POSITION_DETAIL_BY_ID = "/position/getPositionDetail";
  public static final String INSERT_NEW_CANDIDATE = "/candidate/insertNewCandidate";
  public static final String INSERT_NEW_POSITION = "/position/insertNewPosition";
  public static final String UPDATE_CANDIDATE_DETAIL = "/candidate/updateCandidateDetail";
  public static final String UPDATE_CANDIDATE_STATUS = "/candidate/updateCandidateStatus";
  public static final String UPDATE_POSITION_INFORMATION = "/position/updatePositionInformation";


}
