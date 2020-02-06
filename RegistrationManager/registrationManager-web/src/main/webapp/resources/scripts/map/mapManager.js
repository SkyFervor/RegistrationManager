(function(){
	document.write('<script type="text/javascript" src="https://staticfiles.skyfervor.com/BaiduMap/api.js"></script>');
})();

/**
 * 参考的zMap.js
 */
skyfervorMap = {
		mapObj : null,
		mapId : '',
		opts : '',
		zoom_level : {
			country_zoom : 5,
			province_zoom : 7,
			city_zoom : 10,
			area_zoom : 15
		},
		marker : '',
		default_center : '中国'
};

/**
 * 获取地图容器对象
 */
skyfervorMap.getMap = function() {
	return skyfervorMap.mapObj;
};

/**
 * 设置地图容器对象
 */
skyfervorMap.setMap = function(obj) {
	skyfervorMap.mapObj = obj;
}

/**
 * 地图初始化
 */
skyfervorMap.createMap = function(mapId, opts) {
	if (skyfervorMap.getMap()) {
		//清除地图上的覆盖物
		skyfervorMap.getMap().clearOverlay();
	}
	skyfervorMap.mapId = mapId;
	skyfervorMap.setMap(new BMap.Map(mapId));
	skyfervorMap.opts = opts;
	
	//默认参数
	if (opts) {
		var level = opts.level || skyfervorMap.zoom_level.city_zoom;
		if (parseInt(opts.lng) && parseInt(opts.lat)) {
			skyfervorMap.getMap().centerAndZoom(new BMap.Point(opts.lng, opts.lat), level);
		} else if (opts.addr) {
			skyfervorMap.getMap().centerAndZoom(opts.addr, level);
		} else {
			skyfervorMap.getMap().centerAndZoom(skyfervorMap.default_center, level);
		}
	} else {
		skyfervorMap.getMap().centerAndZoom(skyfervorMap.default_center, skyfervorMap.zoom_level.country_zoom);
	}
	
	skyfervorMap.getMap().setDefaultCursor("move");

	//默认添加比例尺
	skyfervorMap.controls.addScale({anchor:BMAP_ANCHOR_TOP_RIGHT});

	setTimeout(function() { //删除版权，挡我视线
		$('#'+mapId).find('.anchorBL').remove();
	}, 1000);
	
}

skyfervorMap.init = function() {
	if (skyfervorMap.getMap()) {
		skyfervorMap.getMap().clearOverlay();
	}
	
	var evts = ['click', 'dblclick', 'dragend'];
	
	for (itm in evts) {
		skyfervorMap.getMap().removeEventListener(evts[itm]);
	}
};

/**
 * 开启功能
 */
skyfervorMap.enables = {
	/**
	 * 启用滚轮放大缩小，默认禁用。
	 */
	scrollWheelZoom: function() {
		skyfervorMap.getMap().enableScrollWheelZoom();
	},
	/**
	 * 开启标注拖拽事件，默认关闭
	 */
	markerDragging : function() {
		skyfervorMap.marker.enableDragging();
		skyfervorMap.marker.setTitle("请拖跩到您的位置");
	}
};

/**
 * 关闭功能
 */
skyfervorMap.disables = {
	/**
	 * 禁用滚轮放大缩小。
	 */
	scrollWheelZoom: function() {
		skyfervorMap.getMap().disableScrollWheelZoom();
	},
	/**
	 * 关闭标注拖拽事件
	 */
	markerDragging : function() {
		skyfervorMap.marker.disableDragging();
	}
};

/**
 * TODO 地图上添加控件会出现重影，本地Demo不会出现，待解决。
 * 控件
 */
skyfervorMap.controls = {
	/**
	 * 地图平移缩放控件，PC端默认位于地图左上方，它包含控制地图的平移和缩放的功能。移动端提供缩放控件，默认位于地图右下方。
	 * @param opts 参数
	 */
	addNavi : function(opts) {
		skyfervorMap.getMap().addControl(new BMap.NavigationControl(opts));
	},
	/**
	 * 比例尺控件，默认位于地图左下方，显示地图的比例关系。
	 * @param opts 参数
	 */
	addScale : function(opts) {
		skyfervorMap.getMap().addControl(new BMap.ScaleControl(opts));
	},
	/**
	 * 缩略地图控件，默认位于地图右下方，是一个可折叠的缩略地图。
	 * @param opts 参数
	 */
	addOverview : function(opts) {
		skyfervorMap.getMap().addControl(new BMap.OverviewMapControl(opts));
	},
	/**
	 * 地图类型控件，默认位于地图右上方。
	 * @param opts 参数
	 */
	addMapType : function(opts) {
		skyfervorMap.getMap().addControl(new BMap.MapTypeControl(opts));
	},
	/**
	 * 定位控件，针对移动端开发，默认位于地图左下方。
	 * @param opts
	 */
	addGeolocation : function(opts) {
		try {
			skyfervorMap.getMap().addControl(new BMap.GeolocationControl(opts));
		}catch(e) {}
	}
};

/**
 * 添加监听事件
 */
skyfervorMap.addListener = function(obj,type,callback) {
	obj.addEventListener(type, function(e) {
		if (callback)
			callback(e.point);
	});
};

/**
 * 弹出提示
 */
skyfervorMap.msgAlert = function(opts, msg, pObj, lng, lat) {
	var infoWindow = new BMap.InfoWindow(msg, opts);
	pObj.openInfoWindow(infoWindow, new BMap.Point(lng, lat));
};

/**
 * 设置地图显示
 * @param lng 经度
 * @param lat 纬度
 * @param level 显示级别
 * @param cityName 城市名称  如果经纬度为0，则用名称来查找地图
 * @return obj.lng 返回当前中心位置的经度  obj.lat 返回当前中心位置的纬度
 */
skyfervorMap.setCenterAndZoom = function(lng, lat, level, cityName) {
	if (lng && lat) {
		skyfervorMap.getMap().centerAndZoom(new BMap.Point(lng, lat), level);
	} else {
		skyfervorMap.getMap().centerAndZoom(cityName, level);
	}
	return skyfervorMap.getMap().getCenter();
};

/**
 * 显示国家
 */
skyfervorMap.setCountryCenterAndZoom = function(lng, lat, countryName) {
	return skyfervorMap.setCenterAndZoom(lng, lat, skyfervorMap.zoom_level.country_zoom, countryName);
};

/**
 * 显示省份
 */
skyfervorMap.setProvinceCenterAndZoom = function(lng, lat, provinceName) {
	return skyfervorMap.setCenterAndZoom(lng, lat, skyfervorMap.zoom_level.province_zoom, provinceName);
};

/**
 * 显示城市
 */
skyfervorMap.setCityCenterAndZoom = function(lng, lat, cityName) {
	return skyfervorMap.setCenterAndZoom(lng, lat, skyfervorMap.zoom_level.city_zoom, cityName);
};

/**
 * 显示县区
 */
skyfervorMap.setCountyCenterAndZoom = function(lng, lat, countyName) {
	return skyfervorMap.setCenterAndZoom(lng, lat, skyfervorMap.zoom_level.area_zoom, countyName);
};

/**
 * 添加标注
 * @param opts 坐标点
 */
skyfervorMap.addMarker = function(opts) {
	//TODO  地图加载完后再添加标注
	skyfervorMap.marker && skyfervorMap.getMap().removeOverlay(skyfervorMap.marker);//先移除原标注
	skyfervorMap.marker = new BMap.Marker(new BMap.Point(opts.lng, opts.lat) || skyfervorMap.getMap().getCenter());
	skyfervorMap.getMap().addOverlay(skyfervorMap.marker);
};

/**
 * 添加标注，并设置移动后回调函数
 * @param opts 坐标点
 * @param callback 回调方法，参数是point
 */
skyfervorMap.addMarkerWithDrag = function(opts, callback) {
	skyfervorMap.addMarker(new BMap.Point(opts.lng, opts.lat));
	skyfervorMap.enables.markerDragging();
	skyfervorMap.addListener(skyfervorMap.marker, 'dragend', callback);
	skyfervorMap.addListener(skyfervorMap.marker, 'dragging', callback);
};

/**
 * 方便使用，直接封装
 * 在地图上添加标注，可点，可移动
 * @param opts 坐标
 * @param callback 回调方法
 */
skyfervorMap.addMarkerWithClickDrag = function(opts, callback) {
	skyfervorMap.addMarkerWithDrag(new BMap.Point(opts.lng, opts.lat), callback);
	skyfervorMap.addListener(skyfervorMap.getMap(), 'click', function(p) {
		skyfervorMap.addMarkerWithDrag(p, callback);
		if (callback)
			callback(p);
	});
};

/**
 * 移动地图到某个位置
 * @param lng 经度
 * @param lat 纬度
 */
skyfervorMap.panTo = function(lng, lat) {
	skyfervorMap.getMap().panTo(new BMap.Point(lng, lat));
};

/**
 * 添加标注，在点击标注时，显示信息
 * @param lng 标注的经度
 * @param lat 标注的纬度
 * @param msg 显示的信息
 */
skyfervorMap.addMarkerWithOpenWindow = function(lng, lat, msg) {
	skyfervorMap.addMarker({"lng" : lng, "lat" : lat});
	skyfervorMap.addListener(skyfervorMap.marker, 'click', function(p) {
		skyfervorMap.msgAlert(null, msg, skyfervorMap.getMap(), p.lng, p.lat);
	});
};



