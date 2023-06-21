//
//  HomeViewController.swift
//  second-hand
//
//  Created by SONG on 2023/06/05.
//

import UIKit

enum Section: CaseIterable {
       case main
}

struct Product: Hashable {
    let id = UUID()
    var title: String
    var price: String
    var location: String
    var registerTime: String
}

final class HomeViewController: NavigationUnderLineViewController, ButtonCustomViewDelegate {
    
    enum Section: CaseIterable {
        case main
    }

    private var productListCollectionView : UICollectionView!
    private var setLocationViewController = SetLocationViewController()
    private var joinViewController = JoinViewController()
    private var productArray = [("선풍기", "25,000원", "역삼동", "4시간전"), ("에어팟", "50,000원", "점봉동", "1시간전"), ("냉장고", "999,999,999원","강남", "1초전"), ("냉장고", "999,999,999원","강남", "1초전"), ("냉장고", "999,999,999원","강남", "1초전"), ("냉장고", "999,999,999원","강남", "1초전"), ("냉장고", "999,999,999원","강남", "1초전"), ("냉장고", "999,999,999원","강남", "1초전")]

    private lazy var products: [Product] = {
        return self.productArray.map { Product(title: $0.0, price: $0.1, location: $0.2, registerTime: $0.3) }
    }()
    private var dataSource: UICollectionViewDiffableDataSource<Section, Product>!
    private var isLogin = false
    private let registerProductButton = UIButton()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setObserver()
        setupDataSource()
        applyInitialSnapshot()
    }
    
    private func applyInitialSnapshot() {
        var snapshot = NSDiffableDataSourceSnapshot<Section, Product>()
        snapshot.appendSections([.main])
        snapshot.appendItems(products, toSection: .main)
 
        dataSource.apply(snapshot, animatingDifferences: false)
    }
    
    private func setObserver() {
        NotificationCenter.default.addObserver(self, selector: #selector(didRecieveLogin(_:)), name: NSNotification.Name("LOGIN"), object: nil)
    }
    
    @objc func didRecieveLogin(_ notification: Notification) {
        self.isLogin = true
        print("로그인 되었습니다.")
    }
    
    private func setUI() {
        let layout = UICollectionViewFlowLayout()
        let figmaCellHight = 152
        let figmaHeight = 852
        
        layout.minimumLineSpacing = .zero
        layout.itemSize = .init(width: self.view.frame.width, height: CGFloat(figmaCellHight*figmaHeight)/self.view.frame.height)
        productListCollectionView = UICollectionView(frame: self.view.frame, collectionViewLayout: layout)
        self.view.addSubview(productListCollectionView)
        setNavigationRightBarButton()
        setNavigationLeftBarButton()
        setRegisterProductButton()
    }
    
    private func setNavigationRightBarButton() {
        let rightBarButton = HomeRightBarButton()
        navigationController?.navigationBar.topItem?.rightBarButtonItem = rightBarButton
    }
    
    private func setNavigationLeftBarButton() {
        let leftBarButton = HomeLeftBarButton()
        let buttonCustomView = leftBarButton.customView as? ButtonCustomView
        buttonCustomView?.delegate = self
        navigationController?.navigationBar.topItem?.leftBarButtonItem = leftBarButton
    }
    
    func tappedSetLocation() {
        present(UINavigationController(rootViewController: setLocationViewController), animated: true)
    }
    
    private func setRegisterProductButton() {
        let registerButtonHeightWidth = CGFloat(56)
        registerProductButton.setImage(UIImage(systemName: "plus"), for: .normal)
        registerProductButton.tintColor = .neutralBackground
        registerProductButton.backgroundColor = .accentBackgroundPrimary
        
        self.view.addSubview(registerProductButton)
        registerProductButton.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            registerProductButton.bottomAnchor.constraint(equalTo: self.view.bottomAnchor, constant: -107),
            registerProductButton.trailingAnchor.constraint(equalTo: self.view.trailingAnchor, constant: -24),
            registerProductButton.widthAnchor.constraint(equalToConstant: registerButtonHeightWidth),
            registerProductButton.heightAnchor.constraint(equalToConstant: registerButtonHeightWidth)
        ])
        
        registerProductButton.clipsToBounds = true
        registerProductButton.layer.cornerRadius = registerButtonHeightWidth / 2
    }
    
    private func setupDataSource() {
        self.productListCollectionView.register(HomeProductCollectionViewCell.self, forCellWithReuseIdentifier: HomeProductCollectionViewCell.identifier)
        
        self.dataSource = UICollectionViewDiffableDataSource<Section, Product>.init(collectionView: productListCollectionView, cellProvider: { collectionView, indexPath, itemIdentifier in
            guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: HomeProductCollectionViewCell.identifier, for: indexPath) as? HomeProductCollectionViewCell else { preconditionFailure() }
            
            cell.configure(title: itemIdentifier.title, price: itemIdentifier.price, location: itemIdentifier.location, registerTime: itemIdentifier.registerTime)

            return cell
        })
    }
}
