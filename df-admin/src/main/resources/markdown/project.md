<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">DingFlow设计文档</h1>

## 一、项目背景

## 二、项目架构


## 三、项目信息

|姓名 | 联系方式 |   负责模块                  |
| ------------ | -------------------|  ------------------ |
| 阿吉       |        qimingjin@126.com     |           全体 |

 

|  项目名称     | 分支        |   备注                  |
| ------------ | -------------------|  ------------------ |
| ding-flow       |    feature/develop1.0.0    |         |

## 四、接口设计

####   4.1 请求格式
目前项目只使用`GET`和`POST`来表示请求内容和更新内容两种语义。

如果需要登录才能访问数据，则需要先向后端请求登录，得到token，然后在后续请求头部携带名称为`X-DF-USER-TOKEN`的token值来访问营销端API。

具体格式为：

    {
        X-DF-USER-TOKEN: VldKmc4SXRFCbqCJNqym4JVFeCbr3AJKbTYiOk3OS6cm9EjA1CEpaVmhBm8WWzyB
    }

具体交互如下：

![项目架构](  https://static.xiaofengwang.com/dev/img/bryuco9bjr040tmw67yk.jpg)

#### 4.2 GET请求
    GET API_URL?params
例如

    GET /role/getRole

或者

    GET /role/getRoleById?id=1

#### 4.3 POST请求
    POST API_URL
    {
        body
    }
例如

    POST /role/list

或者

    POST /role/list
    {
        id: 1
    }

#### 4.4 分页请求参数



###  4.5 响应格式
    Content-Type: application/json;charset=UTF-8
    {
        body
    }
而body是存在一定格式的json内容：

    {
        code: xxx,
        msg: xxx,
        data: {}
    }


### 4.10 本期接口
  接口请求报文和返回报文请参考接口下的地址，以接口文档为准


###  错误码

#### 系统通用错误码
系统通用错误码包括4XX和5XX


## 五、功能设计


 
## 六、项目配置
  暂无全局参数配置

## 七、项目风险
  暂无

## 八、历史数据处理
  本期无历史数据处理