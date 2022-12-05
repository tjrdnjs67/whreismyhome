const pageInfoStore = {
    namespaced: true,
    state: {
        prevPage: "",
        curPage: "",
        isInterestPage: false,
        isHouseMapPage: false,
    },
    mutations: {
        SET_CURRENT_PAGE

    },
};

export default pageInfoStore;
