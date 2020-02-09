<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>
        <@l.logout />
        <span><a href="/user">List of users</a></span>
    </div>
    <div>
        <form method="post">
            <input type="text" name="text" placeholder="Enter a message">
            <input type="text" name="tag" placeholder="Enter a tag">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit">Submit</button>
        </form>
    </div>
    <div>Message List:</div>
    <form method="get" action="/messages">
        <input type="text" name="filter" value="${filter}">
        <button type="submit">Search</button>
    </form>
    <#list messages as message>
    <div>
        <b>${message.id}</b>
        <span>${message.text}</span>
        <i>${message.tag}</i>
        <strong>${message.authorName}</strong>
    </div>
    </#list>
</@c.page>