window.onload = function() {
    //开启debug
    const debug0 = false;
    //diy debug
    window.delog = function(str) {
        if (debug0) {
            console.log(str);
        }
    };
    window.keyChar = 'p';
    //宽方向的方格数
    const w = 10;
    //高方向的方格数
    const h = 25;
    //每个方格边长单位px
    const size = 25;
    //每个方格默认颜色
    window.defaultColor =
        // '#0000';
        "#cecc5e2e";
    //snake body color
    window.changeColor = '#ce5757a6';
    //蛇身体对象数组
    window.foodColor = '#99abbd';
    let bodyList = [];
    //方格对象数组
    let divList = [];
    //根据上面的参数计算初始化界面信息
    let view = calShape(w, h, size);
    delog(view);
    //生成w*h方格.
    divList = createView(view);
    this.view = view;
    this.bodyList = bodyList;
    this.divList = divList;
    initBody();
    genFood();

}

window.setInterval(
    () => {
        keyType(keyChar);
    }, 120);

function reStart(view) {
    keyChar = 'p';
    divList.forEach(val => {
        val.isBody = false;
        val.isFood = false;
    });
    changeDivListColor(divList);
    initBody();
    genFood();
}

function createView(view) {
    let bodyList = [];
    let divList = [];
    let body = document.body;
    let panle = document.createElement('div');
    panle.setAttribute('id', 'panle');
    document.getElementById('main').appendChild(panle);

    panle.style.width = view.width + "px";
    panle.style.height = view.height + "px";
    //生成div
    for (let i = 0; i < view.total; i++) {
        let div0 = document.createElement("div");
        let div1 = document.createElement("div");

        //计算xy坐标值
        let w = i % view.w;
        let h = i / view.w;
        h = h - h % 1;

        let xy = calXy(i, view);

        div1.setAttribute("x", xy.x);
        div1.setAttribute("y", xy.y);
        div0.setAttribute('class', 'parent');
        div1.setAttribute('class', 'child');
        let text = document.createTextNode(" ");
        div0.style.width = view.size + "px";
        div0.style.height = view.size + "px";
        div1.appendChild(text);
        div0.appendChild(div1);
        panle.appendChild(div0);
        divList.push(point(div1, xy.x, xy.y, i, false, false));
        div1.onclick = function() {
            delog(this);
        };
    }
    return divList;
}

function calXy(index, view) {
    //计算xy坐标值
    let x = index % view.w;
    let y = index / view.w;
    y = y - y % 1;
    return {
        x,
        y
    }
}



//根据坐标搜索div
//return div(x.y) in list 
function serachDiv(list, x, y) {
    return list.filter((val) => {
        return val.x == x && val.y == y
    })[0];
}

function serachDivList(list, x, y, view) {
    return list[y * view.w + x]
}

//re Food point.x&point.y
function getFoodXy(bodyList, view) {
    let x = view.w;
    let y = view.h;
    x = getRint(x);
    y = getRint(y);
    while (serachDiv(bodyList, x, y)) {
        x = getRint(x);
        y = getRint(y);
    }
    return {
        x,
        y
    }
}

function initBody() {
    divList[0].el.style['background-color'] = changeColor;
    divList[0].isBody = true;
    let initPoint = point(divList[0].el, 0, 0, 0, true, false);
    bodyList = [];
    bodyList.push(initPoint);
}

//generate food & change its color
function genFood() {
    let foodXy = getFoodXy(bodyList, view);
    let foodPoint = serachDiv(divList, foodXy.x, foodXy.y);
    foodPoint.isFood = true;
    foodPoint.isBody = false;
    changeColorPoint(foodPoint);
    delog("score:" + bodyList.length)
}

//get randomInt
function getRint(x) {
    return Math.floor(Math.random() * x);
}

function showPoint() {
    delog(this);
    let list = [];
    list.push(point(null, 0, 0));
    list.push(point(null, 1, 1));
    list.push(point(null, 2, 2));
    let view = window.view;
    let point0 = getFood(list, view);
    delog(point0);
}


function testBorder(targetXy, view) {

}


//re divlist color
function changeDivListColor(divList) {
    //每个方格默认颜色
    // const defaultColor = "#CECC5E";
    //snake body color
    // const changeColor = '#ce5757a6';
    divList.forEach(
        (val) => {
            if (val.flag) {
                val.el.style['background-color'] = changeColor;
            } else {
                val.el.style['background-color'] = defaultColor;
            }
        });
}

//计算目标方格
function targetPont(list, key) {
    let first = list[0];
    let x = first.x;
    let y = first.y;
    if (key == 'd') {
        x++;
    } else if (key == 's') {
        y++;
    } else if (key == 'w') {
        y--;
    } else if (key == 'a') {
        x--;
    } else if (key == 'q') {

    } else if (key == 'p') {

    } else {
        keyChar = 'p';
        // return null;
    }
    return {
        x,
        y
    }
}

document.onkeypress = (e) => {

    window.keyChar = e.key;
    // keyType(e.key);
    // delog(e.key);
};


function keyType(key) {
    if (key === 'p') {
        return;
    }
    // delog(this.bodyList);
    let bodyList = this.bodyList;
    let divList = this.divList;
    //get target Point(x,y)
    let targetXy = targetPont(this.bodyList, key);
    //calculate bodylidt
    calBodyList(targetXy, this.view);
    //update paint body 
    // moveBody(bodyList, divList, this.view);
    // let point = target(body, key);
}

function calBodyList(targetXy, view) {
    //test edge
    if (targetXy.x >= view.w) {
        return gameOver();
    }
    if (targetXy.y >= view.h) {
        return gameOver();
    }
    if (targetXy.x < 0 || targetXy.y < 0) {
        return gameOver();
    }

    //find target div nextPoint(el,x,y,flag)
    let nextPoint = serachDiv(divList, targetXy.x, targetXy.y);

    if (nextPoint.isBody) {
        return gameOver();
    }
    nextPoint.isBody = true;
    this.bodyList.unshift(nextPoint);

    if (!nextPoint.isFood) {
        //last in body
        let lastPoint = this.bodyList.pop();
        lastPoint.isBody = false;
        lastPoint.isFood = false;
        changeColorPoint(lastPoint);
    } else {
        nextPoint.isFood = false;
        genFood();
    }
    changeColorPoint(nextPoint);
    return this.bodyList;
}

function paintBody(bodyList, divList, view) {
    changeColorPoint(nextPoint);
    changeColorPoint(lastPoint);
    delog(nextPoint);
}

//更新css颜色
function changeColorPoint(point) {
    //每个方格默认颜色
    // let defaultColor = "#CECC5E";
    //蛇身体颜色
    // let changeColor = '#ce5757a6';
    // let foodColor = '#ce5757a6';
    if (point.isBody) {
        point.el.style['background-color'] = changeColor;
    } else {
        point.el.style['background-color'] = defaultColor;
    }
    if (point.isFood)
        point.el.style['background-color'] = foodColor;
}

function gameOver() {
    alert('GameOver' + 'score:' + bodyList.length);
    keyChar = 'p';
    reStart();
    return 0;
}

//方格坐标信息对象
function point(el, x, y, index, isBody, isFood) {
    return {
        el,
        x,
        y,
        index,
        isBody,
        isFood
    }
}

//计算游戏界面盒子w,h
function calShape(w, h, size) {
    return {
        width: w * size + w * 2,
        height: h * size + h * 2,
        total: w * h,
        size: size,
        w,
        h
    }
}