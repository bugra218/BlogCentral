
function setActiveFilter(filterName) {
    let filterElement = document.getElementById(filterName);
    filterElement.className = "uk-active";
}

function previousPage() {
    let currentPath = window.location.href;
    let splitPageNumber = currentPath.lastIndexOf("/")
    let currentPageNumber = parseInt(currentPath.slice(splitPageNumber + 1, (currentPath.length)));
    if (currentPageNumber > 1) {
        window.location.href = (currentPath).slice(0, splitPageNumber) + "/" + (currentPageNumber - 1);
    }
}

function nextPage() {
    let totalPages = document.getElementById("amountOfPages").value;
    let currentPath = window.location.href;
    let splitPageNumber = currentPath.lastIndexOf("/")
    let currentPageNumber = parseInt(currentPath.slice(splitPageNumber + 1, (currentPath.length)));
    if (currentPageNumber < totalPages) {
        window.location.href = (currentPath).slice(0, splitPageNumber) + "/" + (currentPageNumber + 1);
    }

}