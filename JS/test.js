// 模拟愿望清单数据
const wishList = [
    { id: "P1", name: "Product 1", price: 100, date: "2025-03-10T14:30:45.123Z" }, // 改动：因为每个物品只有一个日期属性，统一使用date属性让排序函数可以复用
    { id: "P2", name: "Product 2", price: 200, date: "2025-03-12T14:30:45.123Z" },
    { id: "P3", name: "Product 3", price: 300, date: "2025-03-12T14:30:45.123Z" },
  ];
  
  // 模拟已购买商品列表数据
  const purchasedList = [
    {
      id: "P4",
      name: "Product 4",
      price: 400,
      quantity: 2,
      date: "2025-03-10T14:30:45.123Z", // 改动：因为每个物品只有一个日期属性，统一使用date属性让排序函数可以复用
    },
    {
      id: "P5",
      name: "Product 5",
      price: 500,
      quantity: 1,
      date: "2025-03-12T14:30:45.123Z",
    },
  ];
  
  // 新增帮手函数来减少代码重复度和增加可阅读性
  /**
   * 帮手函数：查找目标物品在目标列表中的索引
   * @param {{}} product
   * @param {*[]} list
   * @returns 目标物品的索引，若物品不存在则返回-1
   */
  function findProductIndex(product, list) {
    return list.findIndex((item) => item.id === product.id);
  }
  
  /**
   * 向愿望清单加入新商品
   * @param {{}} product
   */
  function addToWishList(product) {
    if (findProductIndex(product, wishList) !== -1) {
      console.log("物品已存在于愿望清单中");
      return;
    }
  
    wishList.push(product);
    console.log("添加至愿望清单成功！"); // 加入用户反馈，提示操作成功
  }
  
  /**
   * 将商品从愿望清单中移除
   * @param {{}} product
   */
  function removeFromWishList(product) {
    const index = findProductIndex(product, wishList);
    if (index === -1) {
      console.error("物品不存在于愿望清单中");
      return;
    }
  
    wishList.splice(index, 1);
    console.log("将商品移除出愿望清单成功！");
  }
  
  /**
   * 将商品标记为已购买
   * @param {{}} product
   * @param {number} quantity - 购买的商品数量，默认为1
   * @param {string} date - 购买时的日期和时间，默认为函数执行时的时间
   */
  function markAsPurchased(
    product,
    quantity = 1,
    date = new Date().toISOString()
  ) {
    if (quantity < 1 || typeof quantity !== "number" || isNaN(quantity)) {
      // 检查quantity数量是否有效
      console.error("购买数量不能小于1, 或者无效数量");
      return;
    }
    removeFromWishList(product); // 将该商品从愿望清单中移除
    const purchasedProduct = { ...product, quantity, date }; // 加入新的属性来适配已购买列表
    purchasedList.push(purchasedProduct); // 将商品加入已购买列表
    console.log("标记商品为已购买成功！");
  }
  
  // 改动：将根据价格排序整合至一个函数内
  /**
   * 将列表内商品根据价格排序
   * @param {*[]} list 列表
   * @param {boolean} desc 是否降序
   * @returns 排序过后的新列表
   */
  function sortByPrice(list, desc = false) {
    return list.slice().sort( // 使用slice()来确保不修改原有列表，而是返回一个全新的列表
      (a, b) =>
        desc
          ? b.price - a.price // 价格从高到低排序
          : a.price - b.price // 价格从低至高排序
    );
  }
  
  // 改动：将根据日期排序整合至一个函数内
  /**
   * 将列表内商品根据日期排序
   * @param {*[]} list 列表
   * @param {boolean} desc 是否降序
   * @returns 排序过后的新列表
   */
  function sortByDate(list, desc = false) {
    return list.slice().sort(
      (a, b) =>
        desc
          ? new Date(b.date) - new Date(a.date) // 日期从高到低排序
          : new Date(a.date) - new Date(b.date) // 日期从低到高排序
    );
  }

addToWishList